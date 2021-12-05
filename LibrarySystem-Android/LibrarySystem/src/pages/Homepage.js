import React, {useState} from 'react';
import {View, StyleSheet, Text} from 'react-native';
import {Button, DefaultTheme} from 'react-native-paper';
import axios from 'axios';
import { useFocusEffect } from '@react-navigation/core';
import { FlatList } from 'react-native-gesture-handler';
import { blue100 } from 'react-native-paper/lib/typescript/styles/colors';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';

// Authors: Saagar and Arman. 
// Homepage shows library operating hours and a link to the item catalogue for all users. 

//this is from the vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});


const Homepage = ({navigation}) => {
  const [error, setError] = useState('');
  const [hour, setHour] = useState([]); 
  DefaultTheme.getHours = getHours;


  const getHours = () => {
     
     AXIOS.get("/api/libraryhour/view_library_hours").then(res => {
       let hours = res.data; 
      for (var i=0; i<hours.length; i++) {
        hours[i] =  (hours[i].dayOfWeek[0] + hours[i].dayOfWeek.slice(1).toLowerCase()
        + ' ' + hours[i].startTime.substring(0, 5) +' - ' +  hours[i].endTime.substring(0,5))
        + "\n"; 
      }
      setHour(hours ); 
    })
    .catch(e => {
      if (e.response.data.error) {
        setError(e.response.data.error);
      } else {
        setError(e.response.data);
      }
    });
  }

  return (
    <View>
      <Text style={styles.welcome}>Hi</Text>
      <Text style={styles.welcome}>{DefaultTheme.currentUser.username}</Text>
      <Text style={styles.header}> Library Operating Hours </Text>
      <Text style={styles.h2} onLayout={getHours}> {hour} </Text>

      
      <Button
        title="Go to items"
        onPress={() => {
          navigation.navigate('ItemsStack');
        }}>
        see items
      </Button>
    </View>
  );
};

const styles = StyleSheet.create({
 
  welcome: {
    color: 'blue', 
    fontSize: 20
  },
  header: {
    marginTop: 20,
    color: 'black',
    fontWeight: 'bold',
    fontSize: 25,
  },
  h2: {
    color: 'black',
    fontSize: 20,
    marginLeft: 20,
  },
  red: {
    color: 'red',
  },
});

export default Homepage;
