import React, {useState} from 'react';
import {View} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
import ItemCard from '../components/ItemCard';
import {
  DefaultTheme,
  TextInput,
  Title,
  Button,
  FAB,
  Dialog,
  Portal,
  Paragraph,
  Headline,
} from 'react-native-paper';

const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const ReserveItem = ({route, navigation}) => {
  const [idNum, setIdNum] = useState(
    DefaultTheme.currentUser.isPatron ? DefaultTheme.currentUser.idNum : '',
  );

  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  const submitReservation = () => {
    AXIOS.post(
      'api/itemReservations/create_reservation/?currentUserId=' +
        DefaultTheme.currentUser.idNum +
        '&idNum=' +
        idNum +
        '&itemNumber=' +
        route.params.item.itemNumber +
        '&isCheckedOut=false',
    )
      .then(res => {
        setResponse('Item Reservation Created');
        setError('');
      })
      .catch(e => {
        setResponse('');
        if (e.response.data.error) {
          setError(e.response.data.error);
        } else {
          setError(e.response.data);
        }
      });
  };
  return (
    <>
      <ScrollView>
        <Title style={{textAlign: 'center', marginTop: '20%'}}>
          Reserve item:
        </Title>
        <ItemCard item={route.params.item} />
        <Headline style={{textAlign: 'center'}}>
          Your reservation begins on: {route.params.item.nextAvailableDate}
        </Headline>
        <View style={{marginHorizontal: '20%', marginTop: '10%'}}>
          <TextInput
            label="Patron's id number"
            mode="outlined"
            disabled={DefaultTheme.currentUser.isPatron}
            value={idNum}
            onChangeText={setIdNum}
          />
        </View>
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
                    navigation.navigate('ItemReservationsStack');
                  }
                }}>
                Confirm
              </Button>
            </Dialog.Actions>
          </Dialog>
        </Portal>
      </ScrollView>
      <FAB
        onPress={() => {
          submitReservation();
        }}
        style={{
          zIndex: 1,
          position: 'absolute',
          bottom: 20,
          right: 50,
          alignSelf: 'center',
        }}
        label="Submit"
      />
    </>
  );
};

export default ReserveItem;
