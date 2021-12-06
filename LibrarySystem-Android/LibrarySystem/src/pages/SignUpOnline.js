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

//Author: Nafis
//This file deals with the Sign Up Online page. Users can use this
//page to sign up for an online patron account. Once a new account is
//made, the user will be redirected to the login page to sign in, 
//before they can access all the features.

const SignUpOnline = ({navigation}) => {
  const [firstName, setFirstName] = useState('');
  const[lastName, setLastName] = useState('');
  const[username, setUsername] = useState('');
  const[password, setPassword] = useState('');
  const[confirmPass, setConfirmPass] = useState('');
  const[email, setEmail] = useState('');
  const[address, setAddress] = useState('');
  const[isResident, setIsResident] = useState(false);

  const [hidePass, setHidePass] = useState(true);
  const [error, setError] = useState('');
  const [response, setResponse] = useState('');
  const hasErrors = () => {
    return username.length<4;
  };


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
        label={"Username"} 
        value={username}
        onChangeText={setUsername} 
        placeholder="Please enter username."
      />

      <TextInput
        label="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry={hidePass}
        placeholder="Please enter your password."
        mode="outlined"
        right={
          <TextInput.Icon name="eye" onPress={() => setHidePass(!hidePass)} />
        }
      />

      <TextInput 
        mode="outlined" 
        label={"Email"} 
        value={email}
        onChangeText={setEmail} 
        placeholder="Please enter email."
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

    <Button onPress={() => {
      AXIOS.post(
        "/api/patron/create_patron_online/?username=" +
        username +
        "&password=" +
        password +
        "&first=" +
        firstName +
        "&last=" +
        lastName +
        "&isResident=" +
        isResident +
        "&address=" +
        address +
        "&email=" +
        email
      )
      .then((res) => {
        setResponse('Signed up successfully! Please log in.');
        setError('');
        console.log(res.data);
        navigation.navigate('Login');
      })
      .catch((e) => {
        if (e.response.data.error) {
          setError(e.response.data.error);
        } else {
          setError(e.response.data);
        }
      });
    }}>
      Sign Up
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


export default SignUpOnline;
