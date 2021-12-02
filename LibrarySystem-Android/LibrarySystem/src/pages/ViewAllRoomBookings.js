// author: selena
import React, {useEffect, useState} from 'react';
import {FlatList, View} from 'react-native';
import {
  Button,
  Card,
  Text,
  Title,
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
  AXIOS.get('/api/roombookings/view_roombookings?currentUserId=admin')
    .then(res => {
      setRoomBookings(res.data);
      setLoading(false);
      console.log(res.data);
    })
    .catch(e => {
      console.log(e);
    });
};

const ViewAllRoomBookings = () => {
  const [loading, setLoading] = useState(true);
  const [roombookings, setRoomBookings] = useState([]);
  useEffect(() => {
    getRoomBookings(setLoading, setRoomBookings);
  }, []);
  return (
    <FlatList
      data={roombookings}
      numColumns={2}
      columnWrapperStyle={{marginHorizontal: 20}}
      refreshing={loading}
      style={{alignSelf: 'center'}}
      onRefresh={() => {
        setLoading(true);
        getRoomBookings(setLoading, setRooms);
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
                      currentUserId +
                      '&timeSlotId=' +
                      timeSlotId,
                  )
                    .then(response => {
                      this.visible = true;
                      this.response = 'Room Booking Cancelled';
                      this.error = '';
                      this.roombookings.splice(index, 1);
                    })
                    .catch(e => {
                      this.visible = true;
                      var errorMsg = e.response.data;
                      this.error = errorMsg;
                      this.response = '';
                    });
                }}>
                Cancel
              </Button>
            }
          />
        );
      }}
    />
  );
};

export default ViewAllRoomBookings;
