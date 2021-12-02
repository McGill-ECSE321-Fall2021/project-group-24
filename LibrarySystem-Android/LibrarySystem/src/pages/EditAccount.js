import React from 'react';
import {View, Text} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const EditAccount = () => {
  return (
    <ScrollView>
      <Text>edit account page</Text>
    </ScrollView>
  );
};

export default EditAccount;
