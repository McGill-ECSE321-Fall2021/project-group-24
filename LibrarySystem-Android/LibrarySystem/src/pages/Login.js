import React, {useState} from 'react';
import {View, Text} from 'react-native';
import {
  TextInput,
  Button,
  DefaultTheme,
  Portal,
  Dialog,
  Paragraph,
} from 'react-native-paper';

import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const Login = ({navigation}) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [hidePass, setHidePass] = useState(true);

  const [error, setError] = useState('');
  return (
    <>
      <View>
        <TextInput
          mode="outlined"
          label="Username"
          onChangeText={setUsername}
        />
        <TextInput
          label="Password"
          onChangeText={setPassword}
          secureTextEntry={hidePass}
          mode="outlined"
          right={
            <TextInput.Icon name="eye" onPress={() => setHidePass(!hidePass)} />
          }
        />
        <Button
          onPress={() => {
            console.log('hi');
            AXIOS.post(
              '/api/user/login' +
                '?username=' +
                username +
                '&password=' +
                password,
            )
              .then(res => {
                setError('');
                AsyncStorage.setItem('currentUser', JSON.stringify(res.data));
                DefaultTheme.getCurrentUser();
              })
              .catch(e => {
                if (e.response.data.error) {
                  setError(e.response.data.error);
                } else {
                  setError(e.response.data);
                }
              });
          }}>
          Login
        </Button>
        <Button
          labelStyle={{color: 'green'}}
          onPress={() => {
            navigation.navigate('SignUpOnline');
          }}>
          Don't have an account? Sign up here!
        </Button>
      </View>
      <Portal>
        <Dialog visible={error}>
          <Dialog.Title>Error</Dialog.Title>
          <Dialog.Content>
            <Paragraph>{error}</Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button
              onPress={() => {
                if (error) {
                  setError('');
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

export default Login;
