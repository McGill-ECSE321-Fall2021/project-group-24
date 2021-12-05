
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
import PatronCard from '../components/PatronCard';

// author: selena
// Lists all patrons for librarians to view, as well as buttons to delete and to verify the patrons. 

//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const ViewAllPatrons = () => {
  const [loading, setLoading] = useState(true);
  // list of all patrons
  const [patrons, setPatrons] = useState([]);

  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  const getPatrons = () => {
    if (!DefaultTheme.currentUser.isPatron) {
      AXIOS.get('api/patron/all')
        .then(res => {
          setPatrons(res.data);
          setLoading(false);
          console.log(res.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  };
  // get list patrons when page is loaded
  useEffect(() => {
    getPatrons();
  }, []);
  return (
    <>
      {/* display list of patron */}
      <FlatList
        data={patrons}
        refreshing={loading}
        onRefresh={() => {
          setLoading(true);
          getPatrons(setLoading, setPatrons);
        }}
        renderItem={({item}) => {
          console.log(item);
          return (
            <PatronCard
              patron={item}
              Dbutton={
                // button for delete patron
                <Button
                  onPress={() => {
                    AXIOS.delete(
                      '/api/patron/delete_patron/' +
                        item.idNum +
                        '/?currentUserId=' +
                        DefaultTheme.currentUser.idNum,
                    )
                      .then(res => {
                        setResponse('Patron deleted');
                        setError('');
                        console.log(res.data);
                        setLoading(true);
                        getPatrons();
                      })
                      .catch(e => {
                        setResponse('');
                        if (e.response.data.error) {
                          setError(e.response.data.error);
                        } else {
                          setError(e.response.data);
                        }
                      });
                  }}>
                  Delete
                </Button>
              }
              Vbutton={
                // button for verify patron
                <Button
                  onPress={() => {
                    AXIOS.post('/api/librarians/verify/?idNum=' + item.idNum)
                      .then(res => {
                        setResponse('Patron succesfully verified');
                        setError('');
                        console.log(res.data);
                        setLoading(true);
                        getPatrons();
                      })
                      .catch(e => {
                        setResponse('');
                        if (e.response.data.error) {
                          setError(e.response.data.error);
                        } else {
                          setError(e.response.data);
                        }
                      });
                  }}>
                  Verify
                </Button>
              }
            />
          );
        }}
      />
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

export default ViewAllPatrons;
