import React from 'react';

import {View, Text} from 'react-native';
import {Button} from 'react-native-paper';

import axios from 'axios';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const BrowseAllItems = ({navigation}) => {
  return (
    <View>
      <Button
        onPress={() => {
          navigation.navigate('ReserveItem');
        }}>
        Reserve item
      </Button>
      <Button
        onPress={() => {
          //this is an example of the post request, you can put this directly into the code
          AXIOS.post(
            '/api/rooms/create_room/' +
              'boberto' +
              '?currentUserId=' +
              'admin' +
              '&capacity=' +
              '69',
          );
        }}>
        Create room
      </Button>
    </View>
  );
};

export default BrowseAllItems;
