import React from 'react';
import {View, Text} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const ReserveRoom = () => {
  return (
    <ScrollView>
      <Text>reserve page</Text>
    </ScrollView>
  );
};

export default ReserveRoom;
