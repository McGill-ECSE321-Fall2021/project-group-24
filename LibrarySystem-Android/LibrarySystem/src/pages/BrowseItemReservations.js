import React, {useEffect, useState} from 'react';
import {View, FlatList, Animated} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
import {
  Button,
  Card,
  Text,
  Portal,
  Dialog,
  Paragraph,
  DefaultTheme,
  Searchbar,
  ActivityIndicator,
  Colors,
} from 'react-native-paper';
import ItemCard from '../components/ItemCard';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const BrowseItemReservations = () => {
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
            reserves.map(reservation => {
              AXIOS.get('/api/items/' + reservation.itemNumber).then(res => {
                var copyOfItems = items;
                copyOfItems[reserves.indexOf(reservation)] = reserves;
                setItems(copyOfItems);
                console.log('hello');

                console.log(reservation);
                if (items.length == reserves.length) {
                  setLoading(false);
                }
              });
            });
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
            reserves.map(reservation => {
              AXIOS.get('/api/items/' + reservation.itemNumber).then(
                itemRes => {
                  var copyOfItems = items;
                  copyOfItems[reserves.indexOf(reservation)] = itemRes.data;
                  setItems(copyOfItems);

                  if (items.length == reserves.length) {
                    setTimeout(() => {
                      setLoading(false);
                    }, 1000);
                    console.log('heeeeee');
                    console.log(items);
                  }
                },
              );
            });
          }
        })
        .catch(e => {
          console.log(e);
        });
    }
  };

  useEffect(() => {
    getItemReservations();
  }, []);
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
          data={items}
          refreshing={loading}
          onRefresh={() => {
            setLoading(true);
            getItemReservations();
          }}
          renderItem={({item, index}) => {
            console.log('item index');

            console.log(items[index]);

            return (
              <ItemCard
                key={index}
                item={item}
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
                          .then(res => {
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
