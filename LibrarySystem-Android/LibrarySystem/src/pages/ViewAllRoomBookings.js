// author: selena
import React, {useEffect, useState} from 'react';
import {FlatList, View} from 'react-native';
import {
  Button,
  Card,
  Text,
  Portal,
  Dialog,
  Paragraph,
  DefaultTheme,
} from 'react-native-paper';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
import RoomBookingCard from '../components/RoomBookingCard';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const getRoomBookings = (setLoading, setRoomBookings) => {
  console.log(
    baseUrl +
      'api/roombookings/view_roombookings?currentUserId=' +
      DefaultTheme.currentUser.idNum,
  );
  if (!DefaultTheme.currentUser.isPatron) {
    AXIOS.get(
      baseUrl +
        'api/roombookings/view_roombookings?currentUserId=' +
        DefaultTheme.currentUser.idNum,
    )
      .then(res => {
        setRoomBookings(res.data);
        setLoading(false);
        console.log(res.data);
      })
      .catch(e => {
        console.log(e);
      });
  } else {
    AXIOS.get(
      'api/roombookings/view_roombookings/patron/' +
        DefaultTheme.currentUser.idNum +
        '?currentUserId=' +
        DefaultTheme.currentUser.idNum,
    )
      .then(res => {
        setRoomBookings(res.data);
        setLoading(false);
        console.log(res.data);
      })
      .catch(e => {
        console.log(e);
      });
  }
};

const ViewAllRoomBookings = () => {
  const [loading, setLoading] = useState(true);
  const [roombookings, setRoomBookings] = useState([]);

  const [error, setError] = useState('');
  const [response, setResponse] = useState('');
  useEffect(() => {
    getRoomBookings(setLoading, setRoomBookings);
  }, []);
  return (
    <>
      <FlatList
        data={roombookings}
        numColumns={2}
        columnWrapperStyle={{marginHorizontal: 20}}
        refreshing={loading}
        style={{alignSelf: 'center'}}
        onRefresh={() => {
          setLoading(true);
          getRoomBookings(setLoading, setRoomBookings);
        }}
        renderItem={({item}) => {
          console.log(item);
          return (
            <RoomBookingCard
              roombooking={item}
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
                        console.log(res.data);
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

export default ViewAllRoomBookings;
