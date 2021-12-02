import 'react-native-gesture-handler';
import React, {useState} from 'react';
import {View} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createDrawerNavigator} from '@react-navigation/drawer';

import BrowseAllItems from './src/pages/BrowseAllItems';
import BrowseAllRooms from './src/pages/BrowseAllRooms';
import BrowseItemReservations from './src/pages/BrowseItemReservations';
import EditAccount from './src/pages/EditAccount';

import Homepage from './src/pages/Homepage';
import Login from './src/pages/Login';
import ReserveItem from './src/pages/ReserveItem';
import ReserveRoom from './src/pages/ReserveRoom';
import SignUpForPatron from './src/pages/SignUpForPatron';
import SignUpOnline from './src/pages/SignUpOnline';
import ViewAllLibrarians from './src/pages/ViewAllLibrarians';
import ViewAllPatrons from './src/pages/ViewAllPatrons';
import ViewAllRoomBookings from './src/pages/ViewAllRoomBookings';
import ViewAllShifts from './src/pages/ViewAllShifts';
import axios from 'axios';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

import {
  DefaultTheme,
  Title,
  Provider as PaperProvider,
  Drawer as PaperDrawer,
} from 'react-native-paper';

import AsyncStorage from '@react-native-async-storage/async-storage';
import {ScrollView} from 'react-native-gesture-handler';

const Drawer = createDrawerNavigator();

const getCurrentUser = () => {
  AsyncStorage.getItem('currentUser').then(value => {
    if (value == null) {
      DefaultTheme.setCurrentUser({
        username: null,
        password: null,
        isPatron: null,
        first: null,
      });
      DefaultTheme.setAppLoading(true);
      DefaultTheme.setAppLoading(false);
    } else {
      DefaultTheme.setCurrentUser(JSON.parse(value));
      DefaultTheme.setAppLoading(true);
      DefaultTheme.setAppLoading(false);
    }
  });
};

getCurrentUser();
const App = () => {
  const [loading, setLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState({
    username: null,
    password: null,
    isPatron: null,
    first: null,
  });

  DefaultTheme.setCurrentUser = setCurrentUser;
  DefaultTheme.setAppLoading = setLoading;
  DefaultTheme.currentUser = currentUser;
  DefaultTheme.getCurrentUser = getCurrentUser;

  if (!loading) {
    console.log(DefaultTheme.currentUser);
    return (
      <PaperProvider>
        <NavigationContainer>
          <Drawer.Navigator
            drawerContent={props => {
              return (
                <>
                  <View style={{flex: 1}}>
                    <Title style={{alignSelf: 'center', paddingTop: '5%'}}>
                      {DefaultTheme.currentUser.username
                        ? 'Hi, ' + DefaultTheme.currentUser.firstName
                        : 'Not Logged In'}
                    </Title>
                    <PaperDrawer.Section style={{marginVertical: '3.5%'}}>
                      <PaperDrawer.Item
                        style={{marginBottom: '3.5%'}}
                        icon="home"
                        label="Homepage"
                        onPress={() => {
                          props.navigation.navigate('Homepage');
                        }}
                      />
                    </PaperDrawer.Section>
                    <PaperDrawer.Section style={{marginVertical: '3.5%'}}>
                      <PaperDrawer.Item
                        style={{borderColor: '#CBAACB', borderWidth: 0}}
                        icon="bookshelf"
                        label="Items"
                        onPress={() => {
                          props.navigation.navigate('BrowseAllItems');
                        }}
                      />
                      <PaperDrawer.Item
                        style={{marginBottom: '3.5%'}}
                        icon="book-account"
                        label="Browse Item Reservations"
                        onPress={() => {
                          props.navigation.navigate('BrowseItemReservations');
                        }}
                      />
                    </PaperDrawer.Section>
                    <PaperDrawer.Section style={{marginVertical: '3.5%'}}>
                      <PaperDrawer.Item
                        icon="google-classroom"
                        label="Rooms"
                        onPress={() => {
                          props.navigation.navigate('BrowseAllRooms');
                        }}
                      />
                      <PaperDrawer.Item
                        style={{marginBottom: '3.5%'}}
                        icon="calendar-clock"
                        label="View All Room Bookings"
                        onPress={() => {
                          props.navigation.navigate('ViewAllRoomBookings');
                        }}
                      />
                    </PaperDrawer.Section>
                    <PaperDrawer.Section>
                      <PaperDrawer.Item
                        icon="badge-account-horizontal-outline"
                        label="View All Librarians"
                        onPress={() => {
                          props.navigation.navigate('ViewAllLibrarians');
                        }}
                      />
                      <PaperDrawer.Item
                        icon="account-group"
                        label="View All Patrons"
                        onPress={() => {
                          props.navigation.navigate('ViewAllPatrons');
                        }}
                      />
                      <PaperDrawer.Item
                        style={{marginBottom: '3.5%'}}
                        icon="timetable"
                        label="View All Shifts"
                        onPress={() => {
                          props.navigation.navigate('ViewAllShifts');
                        }}
                      />
                    </PaperDrawer.Section>
                    <PaperDrawer.Section style={{marginTop: '3.5%'}}>
                      <PaperDrawer.Item
                        style={{marginBottom: '3.5%'}}
                        icon="account-edit-outline"
                        label="Edit Account"
                        onPress={() => {
                          props.navigation.navigate('EditAccount');
                        }}
                      />
                    </PaperDrawer.Section>
                  </View>
                  {DefaultTheme.currentUser.username ? (
                    <PaperDrawer.Item
                      style={{backgroundColor: '#FF968A'}}
                      icon="account"
                      label="Logout"
                      onPress={() => {
                        AXIOS.post(
                          'api/user/logout/' +
                            DefaultTheme.currentUser.username,
                        )
                          .then(res => {
                            if (res.status == 200) {
                              AsyncStorage.setItem(
                                'currentUser',
                                JSON.stringify({
                                  username: null,
                                  password: null,
                                  isPatron: null,
                                  first: null,
                                }),
                              );
                            }
                            getCurrentUser();
                          })
                          .catch(e => {
                            console.log(e);
                          });
                      }}
                    />
                  ) : (
                    <PaperDrawer.Item
                      style={{backgroundColor: '#FF968A'}}
                      icon="account"
                      label="Login"
                      onPress={() => {
                        props.navigation.navigate('Login');
                      }}
                    />
                  )}
                </>
              );
            }}>
            <Drawer.Screen
              name="Homepage"
              component={Homepage}
              options={{title: 'Homepage'}}
            />
            <Drawer.Screen
              name="BrowseAllItems"
              component={BrowseAllItems}
              options={{title: 'Browse Items'}}
            />
            <Drawer.Screen
              name="BrowseAllRooms"
              component={BrowseAllRooms}
              options={{title: 'Browse Rooms'}}
            />
            <Drawer.Screen
              name="BrowseItemReservations"
              component={BrowseItemReservations}
              options={{title: 'Item Reservations'}}
            />
            <Drawer.Screen
              name="EditAccount"
              component={EditAccount}
              options={{title: 'Edit Account'}}
            />
            <Drawer.Screen
              name="Login"
              component={Login}
              options={{title: 'Login'}}
            />
            <Drawer.Screen
              name="ReserveItem"
              component={ReserveItem}
              options={{title: 'Reserve Item'}}
            />
            <Drawer.Screen
              name="ReserveRoom"
              component={ReserveRoom}
              options={{title: 'Reserve Room'}}
            />

            <Drawer.Screen
              name="SignUpForPatron"
              component={SignUpForPatron}
              options={{title: 'Sign Up For Patron'}}
            />
            <Drawer.Screen
              name="SignUpOnline"
              component={SignUpOnline}
              options={{title: 'Sign Up Online'}}
            />
            <Drawer.Screen
              name="ViewAllLibrarians"
              component={ViewAllLibrarians}
              options={{title: 'View All Librarians'}}
            />
            <Drawer.Screen
              name="ViewAllPatrons"
              component={ViewAllPatrons}
              options={{title: 'View All Patrons'}}
            />
            <Drawer.Screen
              name="ViewAllRoomBookings"
              component={ViewAllRoomBookings}
              options={{title: 'View All Room Bookings'}}
            />
            <Drawer.Screen
              name="ViewAllShifts"
              component={ViewAllShifts}
              options={{title: 'View All Shifts'}}
            />
          </Drawer.Navigator>
        </NavigationContainer>
      </PaperProvider>
    );
  } else {
    return <View></View>;
  }
};

export default App;
