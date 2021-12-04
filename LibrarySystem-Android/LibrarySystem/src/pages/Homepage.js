import React, {useState} from 'react';
import {View, Text} from 'react-native';
import {Button, DefaultTheme} from 'react-native-paper';

const Homepage = ({navigation}) => {
  return (

    <View>
      <Text>{DefaultTheme.currentUser.username}</Text>
      <Text style={{color: 'blue'}}>Hi</Text>
      <Text> Library Operating Hours </Text>
      
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

export default Homepage;
