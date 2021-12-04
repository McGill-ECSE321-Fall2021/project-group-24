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
} from 'react-native-paper';
import ItemReservationCard from '../components/ItemReservationCard';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const BrowseItemReservations = () => {
  const [loading, setLoading] = useState(true);
  const [itemReservations, setItemReservations] = useState([]);
  const [items, setItems] = useState([]);
  const [itemResults, setItemResults] = useState([]);

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
          setItemReservations(res.data);
          setLoading(false);
          console.log('ITEM reservations if');
          console.log(res.data);
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
          setItemReservations(res.data);
          setLoading(false);
          console.log('ITEM reservations else');
          console.log(res.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
    // if (itemReservations.length == 0) {
    //   setLoading(false);
    // } else {
    //   Promise.all(
    //     itemReservations.map(reservation =>
    //       AXIOS.get('/api/items/' + reservation.itemNumber).then(res => {
    //         setItems[itemReservations.indexOf(reservation)] = {
    //           ...res.data,
    //         };

    //         if (items.length == itemReservations.length) {
    //           setLoading(false);
    //         }
    //       }),
    //     ),
    //   );
    //   setItemResults(items);
    // }
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
      <FlatList
        data={(itemReservations, items)}
        refreshing={loading}
        onRefresh={() => {
          setLoading(true);
          getItemReservations();
        }}
        renderItem={({item}) => {
          console.log('Item Reservation: ' + item);
          console.log('Item Title: ' + item.idNum);

          return (
            <ItemReservationCard
              item={item}
              buttons={
                <Button
                  onPress={() => {
                    AXIOS.delete(
                      '/api/roombookings/delete_roombooking' +
                        '?currentUserId=' +
                        DefaultTheme.currentUser.idNum +
                        '&timeSlotId=' +
                        item.timeSlotId,
                    )
                      .then(res => {
                        setResponse('Room booking cancelled');
                        setError('');
                        console.log('hi' + res.data);
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
              }
            />
          );
        }}
      />
      {/* <Portal>
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
      </Portal> */}
    </>
  );
};

export default BrowseItemReservations;
