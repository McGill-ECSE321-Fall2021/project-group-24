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
} from 'react-native-paper';

const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});

const CheckoutItem = ({route, navigation}) => {
  console.log(route.params.item);
  const [idNum, setIdNum] = useState(
    route.params.idNum ? route.params.idNum : '',
  );

  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  const submitCheckout = () => {
    AXIOS.put(
      'api/itemReservations/checkout_item/' +
        route.params.item.itemNumber +
        '/byPatron/' +
        idNum +
        '?currentUserId=' +
        DefaultTheme.currentUser.idNum,
    )
      .then(() => {
        setResponse('Item Checked Out');
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
          Check out item:
        </Title>
        <ItemCard item={route.params.item} />
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
                    navigation.pop();
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
          submitCheckout();
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

export default CheckoutItem;
