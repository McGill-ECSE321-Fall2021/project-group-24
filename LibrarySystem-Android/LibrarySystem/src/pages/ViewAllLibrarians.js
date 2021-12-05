import React, {useEffect, useState} from 'react';
import {FlatList, View} from 'react-native';
import {
  Button,
  Card,
  Text,
  Portal,
  Dialog,
  Paragraph,
  DefaultTheme,
} from 'react-native-paper';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
import LibrarianCard from '../components/LibrarianCard.js';
import { useNavigation } from '@react-navigation/core';
// Author: Arman. Gives list of all librarians for other librarians to view.
// Also provides a button for a calendar of everyone's shifts and a button to view a particular librarian's shifts

//this is from the vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const ViewAllLibrarians = () => {
  const [loading, setLoading] = useState(true);
  // list of all librarians stored here
  const [librarians, setLibrarians] = useState([]);

  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  // Uses http get request on backend, only for librarians
  const getLibrarians = () => {
    if (!DefaultTheme.currentUser.isPatron) {
      AXIOS.get('/api/librarians/all/?currentUserId=admin')
        .then(res => {
          setLibrarians(res.data);
          setLoading(false);
          console.log(res.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  };
  const navigation = useNavigation(); 
  // get list of librarians once the page is loaded
  useEffect(() => {
    getLibrarians();
  }, []);
  return (
    <>
    <Button 
      title="View All Shifts"
      onPress={() => navigation.navigate('ViewAllShifts')}>
        View All Shifts
      </Button>
    
      {/* displays the list of librarians */}
      <FlatList
        data={librarians}
        refreshing={loading}
        onRefresh={() => {
          setLoading(true);
          getLibrarians(setLoading, setLibrarians);
        }}
        renderItem={({item}) => {
          console.log(item);
          return (
  
            <LibrarianCard
              librarian={item}

              someShiftsButton={
               // button for viewing this librarian's shifts
                <Button
                  onPress={() => {
                    AXIOS.get('/api/shift/view_librarian_shifts/?currentUserId=admin&librarianId='
                    +item.idNum)
                      .then(res => {
                        let shifts = [];
                        shifts = res.data; 
                        // makes the shifts into a readable format, with each shift given a row (format "Monday 9:00-18:00")
                        for (var i=0; i<shifts.length; i++) {
                          shifts [i] = "\n" + (shifts[i].dayOfWeek[0] + shifts[i].dayOfWeek.slice(1).toLowerCase()
                          + ' ' + shifts[i].startTime.substring(0, 5) +' - ' +  shifts[i].endTime.substring(0,5)); 
                        }
                        // removes the comma between shifts
                        let shiftsText = shifts.join(''); 
                        // displays the shifts via the response dialog box
                        setResponse( item.firstName + ' ' + item.lastName + "'s " + 'Shifts:  ' + shiftsText);
                        setError(''); 
                        console.log(res.data);
                      })
                      .catch(e => {
                       // setResponse("");
                        if (e.response.data.error) {
                          setError(e.response.data.error);
                        } else {
                          setError(e.response.data);
                        }
                      });
                  }}>
                  View Their Shifts
                </Button>
  
              }
            />
          );
        }}
      />
      {/*Dialog box shows errors or the librarian's shifts */}
      <Portal>
        <Dialog visible={error || response}>
          <Dialog.Title>{error ? 'Error' : 'Response'}</Dialog.Title>
          <Dialog.Content>
            <Paragraph>{error ? error : response}</Paragraph>
          </Dialog.Content>
          <Dialog.Actions>
            <Button
              onPress={() => {
                setError('');
                setResponse('');
              }}>
              Ok
            </Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </>
  );
};


export default ViewAllLibrarians;
