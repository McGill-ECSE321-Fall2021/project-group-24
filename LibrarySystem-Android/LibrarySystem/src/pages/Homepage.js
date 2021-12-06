import React, {useState} from 'react';
import {View, StyleSheet, Text} from 'react-native';
import {DefaultTheme, Headline, Button} from 'react-native-paper';
import axios from 'axios';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';

// Authors: Saagar and Arman.
// Homepage shows library operating hours and a link to the item catalogue for all users.

//this is from the vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const Homepage = ({navigation}) => {
  const [hour, setHour] = useState([]);
  DefaultTheme.getHours = getHours;

  const getHours = () => {
    // Retrieves operating hours with get request and reformats so they are presentable
    AXIOS.get('/api/libraryhour/view_library_hours')
      .then(res => {
        let hours = res.data;
        // E.g. Monday 9:00 - 18:00
        for (var i = 0; i < hours.length; i++) {
          hours[i] =
            hours[i].dayOfWeek[0] +
            hours[i].dayOfWeek.slice(1).toLowerCase() +
            ' ' +
            hours[i].startTime.substring(0, 5) +
            ' - ' +
            hours[i].endTime.substring(0, 5) +
            '\n';
        }
        setHour(hours);
      })
      .catch(e => {
        console.log(e);
      });
  };

  return (
    <View style={{justifyContent: 'center', height: '100%'}}>
      <Headline style={{textAlign: 'center', fontWeight: 'bold'}}>
        Montreal Regional Library
      </Headline>
      <Text style={styles.header}>Library Operating Hours:</Text>
      <Text style={styles.h2} onLayout={getHours}>
        {hour}
      </Text>
      <View style={{marginHorizontal: '20%'}}>
        <Button
          icon={'bookshelf'}
          style={{marginVertical: 10}}
          mode="contained"
          onPress={() => {
            navigation.navigate('ItemsStack');
          }}>
          See items
        </Button>
        <Button
          icon={'google-classroom'}
          style={{marginVertical: 10}}
          mode="contained"
          onPress={() => {
            navigation.navigate('RoomsStack');
          }}>
          See rooms
        </Button>
        {!DefaultTheme.currentUser.username && (
          <Button
            icon={'account'}
            style={{marginVertical: 10}}
            mode="contained"
            onPress={() => {
              navigation.navigate('Login');
            }}>
            Login
          </Button>
        )}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  header: {
    textAlign: 'center',
    marginTop: 20,
    color: 'black',
    fontSize: 25,
  },
  h2: {
    textAlign: 'center',
    color: 'black',
    fontSize: 20,
  },
});

export default Homepage;
