import React, {useEffect, useState} from 'react';
import {View, FlatList} from 'react-native';
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
  const [itemReservations, setItemReservations] = useState([]);
  const [itemResults, setItemResults] = useState([]);
  const [items, setItems] = useState([]);
  const [error, setError] = useState('');
  const [response, setResponse] = useState('');
  const [results, setResults] = useState([]);

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
          setItemReservations(res.data);
          console.log('ITEM reservations if');
          console.log(res.data);
          getItems(res.data);
          setResults(res.data);
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
          setItemReservations(res.data);
          console.log('reservations');
          console.log(reservations.length);
          console.log('ITEM RESERVATIONS');
          console.log(itemReservations.length);
          getItems(res.data);
          setResults(res.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  };

  const getItems = copyOfReservations => {
    console.log('copy of res');

    console.log(copyOfReservations);
    if (copyOfReservations.length == 0) {
      console.log('length is 0');
      setLoading(false);
    } else {
      console.log('copyOfReservations');

      console.log(copyOfReservations);
      copyOfReservations.map(reservation => {
        AXIOS.get('/api/items/' + reservation.itemNumber).then(res => {
          var copyOfItems = items;
          copyOfItems[copyOfReservations.indexOf(reservation)] = res.data;
          setItems(copyOfItems);
          console.log('hello');

          console.log(reservation);
          if (items.length == copyOfReservations.length) {
            setItemResults(items);

            setLoading(false);
          }
        });
      });
    }
  };

  const search = query => {
    setResults(
      items.filter(item => {
        if (
          item.itemTitle.toLowerCase().includes(query.toLowerCase()) ||
          item.itemNumber.toLowerCase().includes(query.toLowerCase()) ||
          item.description.toLowerCase().includes(query.toLowerCase()) ||
          (item.author &&
            item.author.toLowerCase().includes(query.toLowerCase())) ||
          (item.genre &&
            item.genre.toLowerCase().includes(query.toLowerCase())) ||
          (item.publisher &&
            item.publisher.toLowerCase().includes(query.toLowerCase())) ||
          (item.issueNumber &&
            item.issueNumber.toLowerCase().includes(query.toLowerCase())) ||
          (item.movieCase &&
            item.movieCase.toLowerCase().includes(query.toLowerCase())) ||
          (item.productionCompany &&
            item.productionCompany
              .toLowerCase()
              .includes(query.toLowerCase())) ||
          (item.director &&
            item.director.toLowerCase().includes(query.toLowerCase())) ||
          (item.producer &&
            item.producer.toLowerCase().includes(query.toLowerCase())) ||
          (item.artist &&
            item.artist.toLowerCase().includes(query.toLowerCase())) ||
          (item.recordingLabel &&
            item.recordingLabel.toLowerCase().includes(query.toLowerCase())) ||
          (item.type && item.type.toLowerCase().includes(query.toLowerCase()))
        ) {
          return true;
        } else {
          return false;
        }
      }),
    );
  };

  useEffect(() => {
    getItemReservations();
  }, []);
  return (
    <>
      <Searchbar onChangeText={search} />
      {loading ? (
        <ActivityIndicator
          style={{flex: 1}}
          animating={true}
          color={Colors.blue800}
        />
      ) : (
        <FlatList
          data={itemReservations}
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
