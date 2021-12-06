import React, {useState} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
import { DefaultTheme, Headline, Subheading, TextInput, Button, Portal, Dialog, Paragraph } from 'react-native-paper';
import { NavigationContainer } from '@react-navigation/native';

const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';

//Author: Nafis
//This file describes the Settings page as well as carries out
//the backend api calls for the following services: deleting 
//patron account, changing user account password and changing
//user account details(first name, last name, email, address).


var AXIOS = axios.create({
  baseURL: baseUrl,
});

const Settings = ({navigation}) => {
  const[username, setUsername] = useState(DefaultTheme.currentUser.username);
  const [firstName, setFirstName] = useState(DefaultTheme.currentUser.firstName);
  const [lastName, setLastName] = useState(DefaultTheme.currentUser.lastName);
  const[email, setEmail] = useState(DefaultTheme.currentUser.email);
  const[address, setAddress] = useState(DefaultTheme.currentUser.address);
  const[newPassword, setNewPassword] = useState('');
  const[patron, setPatronInfo] = useState('');
  const[currentPassword, setCurrentPassword] = useState('');
  const[enterUserID, saveEnteredUserID] = useState('');
  const[userID, setUserID] = useState(DefaultTheme.currentUser.idNum);


  const [hidePass, setHidePass] = useState(true);
  const [error, setError] = useState('');
  const [response, setResponse] = useState('');
  return (
    <>
      <ScrollView>
        <Headline style={{textAlign: 'center', fontWeight: 'bold'}}>Change Basic Info:</Headline>
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

        <Button onPress={()=>{
          AXIOS.put(
            "/api/user/change_name/?username=" +
              username +
              "&firstName=" +
              firstName +
              "&lastName=" +
              lastName
          )
            .then((res) => {
              console.log(res.data);
              if (res.status == 200 || res.status == 201) {
                AXIOS.put(
                  "/api/user/change_email/?username=" +
                    username +
                    "&email=" +
                    email
                )
                  .then((res) => {
                    console.log(res.status);
                    if (res.status == 200 || res.status === 201) {
                      AXIOS.put(
                        "/api/user/change_address/?username=" +
                          username +
                          "&address=" +
                          address
                      )
                        .then((res) => {
                          setResponse('User details successfully changed!');
                          setError('');
                        })
                        .catch((e) => {
                          setResponse('');
                          if (e.response.data.error) {
                            setError(e.response.data.error);
                          } else {
                            setError(e.response.data);
                          }
                        });
                    }
                  })
                  .catch((e) => {
                    setResponse('');
                    if (e.response.data.error) {
                      setError(e.response.data.error);
                    } else {
                      setError(e.response.data);
                    }
                  });
              }
            })
            .catch((e) => {
              setResponse('');
              if (e.response.data.error) {
                setError(e.response.data.error);
              } else {
                setError(e.response.data);
              }
            });
        }}>
          Save Changes.
        </Button>
        

        <Headline style={{textAlign: 'center', fontWeight: 'bold', marginVertical:'10%'}}>Change Password:</Headline>

        <TextInput
        label="Current password"
          value={currentPassword}
          onChangeText={setCurrentPassword}
          secureTextEntry={hidePass}
          placeholder="Please enter your current password."
          mode="outlined"
          right={
            <TextInput.Icon name="eye" onPress={() => setHidePass(!hidePass)} />
          }
        />


        <TextInput
        label="New password"
          value={newPassword}
          onChangeText={setNewPassword}
          secureTextEntry={hidePass}
          placeholder="Please enter your new password."
          mode="outlined"
          right={
            <TextInput.Icon name="eye" onPress={() => setHidePass(!hidePass)} />
          }
        />
        <View style = {{justifyContent:'center', marginHorizontal: '20%'}}>
          <Button
            style={{marginVertical:20}}
            mode="contained"
            onPress={() => {
              AXIOS.put(
                "/api/user/change_password/?username=" +
                username +
                "&newPass=" +
                newPassword +
                "&oldPass=" +
                currentPassword
              )
              .then((res) => {
                setResponse('Successfully changed password.');
                setError('');
                console.log(res.data);
              })
              .catch((e) => {
                if (e.response.data.error) {
                  setError(e.response.data.error);
                } else {
                  setError(e.response.data);
                }
              });
            }}>
              Change Password
          </Button>


          <Headline style={{textAlign: 'center', fontWeight: 'bold', marginVertical:'15%'}}>Delete Account:</Headline>
          <TextInput 
            mode="outlined" 
            label={"UserID"} 
            value={enterUserID}
            onChangeText={saveEnteredUserID} 
            placeholder="Enter ID of account you want to delete."
          />

          
          <Button
            mode="outlined"
            onPress={() => {
              AXIOS.delete(
                "/api/patron/delete_patron/" +
                enterUserID +
                "/?currentUserId=" +
                userID
              )
              .then((res) => {
                console.log(res.status);
                if (res.status == 200) {
                  setResponse('Deleted account successfully.');
                  setError('');
                  console.log(res.data);
                  navigation.navigate('Homepage');
                }
              })
              .catch((e) => {
                if (e.response.data.error) {
                  setError(e.response.data.error);
                } else {
                  setError(e.response.data);
                }
              });
            }}>
              Delete Account
          </Button>
        </View>



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

export default Settings;
