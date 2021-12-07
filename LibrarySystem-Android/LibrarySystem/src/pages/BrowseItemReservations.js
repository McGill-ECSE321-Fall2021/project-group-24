import React, {useEffect, useState} from 'react';
import {FlatList} from 'react-native';
import axios from 'axios';

import {
  Button,
  Portal,
  Dialog,
  Paragraph,
  DefaultTheme,
  ActivityIndicator,
  Colors,
} from 'react-native-paper';
import {useIsFocused} from '@react-navigation/native';
import ItemCard from '../components/ItemCard';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const BrowseItemReservations = ({navigation}) => {
  const [loading, setLoading] = useState(true);
  const [reservations, setReservations] = useState([]);

  const [items, setItems] = useState([]);
  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  const getItemReservations = () => {
    if (DefaultTheme.currentUser.isPatron) {
      AXIOS.get(
        baseUrl +
          'api/itemReservations/patron/' +
          DefaultTheme.currentUser.idNum +
          '?currentUserId=' +
          DefaultTheme.currentUser.idNum,
      )
        .then(res => {
          setReservations(res.data);
          var reserves = res.data;
          if (reserves.length == 0) {
            setLoading(false);
          } else {
            Promise.all(
              reserves.map(async reservation => {
                await AXIOS.get('/api/items/' + reservation.itemNumber).then(
                  res => {
                    var copyOfItems = items;
                    copyOfItems[reserves.indexOf(reservation)] = res.data;
                    setItems(copyOfItems);

                    if (items.length == reserves.length) {
                      setLoading(false);
                    }
                  },
                );
              }),
            );
          }
        })
        .catch(e => {
          console.log(e);
        });
    } else {
      AXIOS.get(
        'api/itemReservations/all' +
          '?currentUserId=' +
          DefaultTheme.currentUser.idNum,
      )
        .then(res => {
          setReservations(res.data);
          var reserves = res.data;
          if (reserves.length == 0) {
            setLoading(false);
          } else {
            Promise.all(
              reserves.map(async reservation => {
                await AXIOS.get('/api/items/' + reservation.itemNumber).then(
                  itemRes => {
                    console.log('doing');
                    var copyOfItems = items;
                    copyOfItems[reserves.indexOf(reservation)] = itemRes.data;
                    setItems(copyOfItems);

                    if (items.length == res.data.length) {
                      setLoading(false);
                    }
                  },
                );
              }),
            );
          }
        })
        .catch(e => {
          console.log(e);
        });
    }
  };
  const isFocused = useIsFocused();
  useEffect(() => {
    setLoading(true);
    getItemReservations();
  }, [isFocused]);
  return (
    <>
      {loading ? (
        <ActivityIndicator
          style={{flex: 1}}
          animating={true}
          color={Colors.blue800}
        />
      ) : (
        <FlatList
          data={reservations}
          refreshing={loading}
          onRefresh={() => {
            setLoading(true);
            getItemReservations();
          }}
          renderItem={({item, index}) => {
            if (!items[index]) {
              setLoading(true);
              getItemReservations();
            } else
              return (
                <ItemCard
                  key={index}
                  item={items[index]}
                  reservationDetails={reservations[index]}
                  buttons={
                    <>
                      <Button
                        onPress={() => {
                          AXIOS.put(
                            '/api/itemReservations/renew/' +
                              reservations[index].itemReservationId +
                              '?currentUserId=' +
                              DefaultTheme.currentUser.idNum,
                          )
                            .then(() => {
                              setResponse('Item Renewed!');
                              setError('');
                              getItemReservations();
                            })
                            .catch(e => {
                              setResponse('');
                              if (e.response.data.error) {
                                setError(e.response.data.error);
                              } else {
                                setError(e.response.data);
                              }
                            });
                        }}>
                        Renew
                      </Button>

                      <Button
                        onPress={() => {
                          AXIOS.delete(
                            '/api/itemReservations/cancel/' +
                              reservations[index].itemReservationId +
                              '?currentUserId=' +
                              DefaultTheme.currentUser.idNum,
                          )
                            .then(res => {
                              setResponse('Item Reservation Cancelled');
                              setError('');
                              setLoading(true);
                              getItemReservations();
                            })
                            .catch(e => {
                              setResponse('');

                              if (e.response.data.error) {
                                setError(e.response.data.error);
                              } else {
                                setError(e.response.data);
                              }
                            });
                        }}>
                        Cancel
                      </Button>
                      {!items[index].isCheckedOut && (
                        <Button
                          item={items[index]}
                          buttons={null}
                          //this is how to pass an object to the next page. Take a look at
                          //ReserveRoom.js to see how the item is used there.
                          onPress={() => {
                            navigation.navigate('CheckoutItem', {
                              item: items[index],
                            });
                          }}>
                          Checkout
                        </Button>
                      )}
                    </>
                  }
                />
              );
          }}
        />
      )}
      <Portal>
        <Dialog visible={error || response}>
          <Dialog.Title>{error ? 'Error' : 'Response'}</Dialog.Title>
          <Dialog.Content>
            <Paragraph>{error ? error : response}</Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button
              onPress={() => {
                if (error) {
                  setError('');
                  setResponse('');
                } else {
                  setError('');
                  setResponse('');
                }
              }}>
              Ok
            </Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </>
  );
};

export default BrowseItemReservations;
