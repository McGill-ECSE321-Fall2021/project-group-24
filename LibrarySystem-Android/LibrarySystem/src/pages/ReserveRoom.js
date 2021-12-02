import React from 'react';
import {View, Text} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
import RoomCard from '../components/RoomCard';
import {Title} from 'react-native-paper';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const ReserveRoom = ({route, navigation}) => {
  return (
    <ScrollView>
      <Title style={{textAlign: 'center'}}>Reserve room:</Title>
      <View style={{alignItems: 'center'}}>
        <RoomCard room={route.params.room} />
      </View>
    </ScrollView>
  );
};

export default ReserveRoom;
