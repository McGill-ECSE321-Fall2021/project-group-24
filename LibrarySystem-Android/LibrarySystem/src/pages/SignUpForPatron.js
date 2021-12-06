import React, {useState} from 'react';
import {View, StyleSheet, Text} from 'react-native';
import {TextInput, Portal, Dialog, Paragraph, Checkbox, HelperText, Button, DefaultTheme} from 'react-native-paper';

import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {ScrollView} from 'react-native-gesture-handler';
import { NavigationContainer } from '@react-navigation/native';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const SignUpForPatron = ({navigation}) => {
  const [firstName, setFirstName] = useState('');
  const[lastName, setLastName] = useState('');
  const[address, setAddress] = useState('');
  const[isResident, setIsResident] = useState(false);

  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  return (
    <>  
      <ScrollView>
        <TextInput 
          mode="outlined" 
          label={"First Name"} 
          value={firstName}
          onChangeText={setFirstName} 
          placeholder="Please enter your first name."
        />

        <TextInput 
          mode="outlined" 
          label={"Last Name"} 
          value={lastName}
          onChangeText={setLastName} 
          placeholder="Please enter your last name."
        />

        <TextInput 
          mode="outlined" 
          label={"Address"} 
          value={address}
          onChangeText={setAddress} 
          placeholder="Please enter your address."
        />

        <View style={styles.checkboxContainer}>
          <Text style={styles.label}>I'm a resident of Montreal.</Text>
          <Checkbox
          status={isResident ? 'checked' : 'unchecked'}
          label="I am a resident of Montreal."
          onPress={() => {
            setIsResident(!isResident);
          }}
        />
      
      </View>
      
      <Button onPress={()=>{
        AXIOS.post(
          "/api/patron/create_patron_irl/?first=" +
          firstName +
          "&last=" +
          lastName +
          "&isResident=" +
          isResident +
          "&address=" +
          address
        )
        .then((res) => {
          setResponse('Signed up patron successfully!');
          setError('');
          console.log(res.data);
          navigation.navigate('Homepage');
        })
        .catch((e) => {
          if (e.response.data.error) {
            setError(e.response.data.error);
          } else {
            setError(e.response.data);
          }
        });
      }}>
        Sign Up Patron
      </Button>

      </ScrollView>

      <Portal>
        <Dialog visible={error || response}>
          <Dialog.Title>{error ? 'Error' : 'Response'}</Dialog.Title>
          <Dialog.Content>
            <Paragraph>{error ? error : response}</Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button
              onPress={() => {
                if (error) {
                  setError('');
                  setResponse('');
                } else {
                  setError('');
                  setResponse('');
                }
              }}>
              Ok
            </Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>

    </>  
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  checkboxContainer: {
    flexDirection: "row",
    marginBottom: 20,
  },
  checkbox: {
    alignSelf: "center",
  },
  label: {
    margin: 8,
  },
});


export default SignUpForPatron;
