import React, {useEffect, useState} from 'react';

import {FlatList} from 'react-native';
import {Button, DefaultTheme, Searchbar} from 'react-native-paper';
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
  const [results, setResults] = useState([]);

  const getRooms = () => {
    AXIOS.get('/api/rooms/view_all_rooms/')
      .then(res => {
        setRooms(res.data);
        setLoading(false);
        setResults(res.data);
        console.log(res.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  const search = query => {
    setResults(
      rooms.filter(room => {
        if (room.roomNum.toLowerCase().includes(query.toLowerCase())) {
          return true;
        } else {
          return false;
        }
      }),
    );
  };

  useEffect(() => {
    getRooms();
  }, []);
  return (
    <>
      <Searchbar onChangeText={search} />
      <FlatList
        data={results}
        numColumns={2}
        refreshing={loading}
        style={{alignSelf: 'center', width: '100%'}}
        onRefresh={() => {
          setLoading(true);
          getRooms();
        }}
        renderItem={({item}) => {
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
    </>
  );
};

export default BrowseAllRooms;
