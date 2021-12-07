// author: selena
import React, {useEffect, useState} from 'react';
import {FlatList} from 'react-native';
import {
  Button,
  Portal,
  Dialog,
  Paragraph,
  DefaultTheme,
} from 'react-native-paper';
import axios from 'axios';
import {useIsFocused} from '@react-navigation/native';

const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
import RoomBookingCard from '../components/RoomBookingCard';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const ViewAllRoomBookings = () => {
  const [loading, setLoading] = useState(true);
  // state variable to keep all the room bookings after getting them
  const [roombookings, setRoomBookings] = useState([]);

  // state variables for responses of controller calls
  const [error, setError] = useState('');
  const [response, setResponse] = useState('');
  // backend call to controller method to get room bookings
  const getRoomBookings = () => {
    // get all roombookings if the current user is a librarian
    if (!DefaultTheme.currentUser.isPatron) {
      AXIOS.get(
        baseUrl +
          'api/roombookings/view_roombookings?currentUserId=' +
          DefaultTheme.currentUser.idNum,
      )
        .then(res => {
          setRoomBookings(res.data);
          setLoading(false);
        })
        .catch(e => {
          console.log(e);
        });
    } else {
      // if the current user is a patron, get the roombookings that belong to the patron
      AXIOS.get(
        'api/roombookings/view_roombookings/patron/' +
          DefaultTheme.currentUser.idNum +
          '?currentUserId=' +
          DefaultTheme.currentUser.idNum,
      )
        .then(res => {
          setRoomBookings(res.data);
          setLoading(false);
        })
        .catch(e => {
          console.log(e);
        });
    }
  };
  // I added isFocused so that when the screen is focused again, it automatically refreshes.
  // -- Saagar
  const isFocused = useIsFocused();
  useEffect(() => {
    getRoomBookings();
  }, [isFocused]);
  return (
    <>
      <FlatList
        data={roombookings}
        refreshing={loading}
        onRefresh={() => {
          setLoading(true);
          getRoomBookings(setLoading, setRoomBookings);
        }}
        renderItem={({item}) => {
          return (
            // display each roombooking
            <RoomBookingCard
              roombooking={item}
              buttons={
                // button for deleting the room booking
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

                        setLoading(true);
                        getRoomBookings();
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
      {/* pop up panel for reponse of controller calls */}
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
