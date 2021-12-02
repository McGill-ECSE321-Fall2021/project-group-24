import React, {useEffect, useState} from 'react';

import {FlatList, View, Image} from 'react-native';
import {
  Button,
  Card,
  Text,
  Title,
  Paragraph,
  DefaultTheme,
} from 'react-native-paper';
import axios from 'axios';
import RoomCard from '../components/RoomCard';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const BrowseAllRooms = ({navigation}) => {
  const [loading, setLoading] = useState(true);
  const [rooms, setRooms] = useState([]);

  const getRooms = () => {
    AXIOS.get('/api/rooms/view_all_rooms/')
      .then(res => {
        setRooms(res.data);
        setLoading(false);
        console.log(res.data);
      })
      .catch(e => {
        console.log(e);
      });
  };
  useEffect(() => {
    getRooms();
  }, []);
  return (
    <FlatList
      data={rooms}
      numColumns={2}
      columnWrapperStyle={{marginHorizontal: 20}}
      refreshing={loading}
      style={{alignSelf: 'center'}}
      onRefresh={() => {
        setLoading(true);
        getRooms();
      }}
      renderItem={({item}) => {
        console.log(item);
        return (
          <RoomCard
            room={item}
            buttons={
              DefaultTheme.currentUser.username && (
                <Button
                  onPress={() => {
                    navigation.navigate('ReserveRoom', {room: item});
                  }}>
                  Reserve
                </Button>
              )
            }
          />
        );
      }}
    />
  );
};

export default BrowseAllRooms;
