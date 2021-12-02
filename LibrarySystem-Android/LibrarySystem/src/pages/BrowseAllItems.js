import React, {useEffect, useState} from 'react';
import {FlatList} from 'react-native';
import {Button} from 'react-native-paper';

import axios from 'axios';
import ItemCard from '../components/ItemCard';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});
//same as vue file

const BrowseAllItems = ({navigation}) => {
  const getItems = () => {
    AXIOS.get('/api/items/all/')
      .then(res => {
        setItems(res.data);
        setLoading(false);
        setResults(res.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  //use 'state variables' for when you want data to change. ie for when u pull from database
  const [loading, setLoading] = useState(true);
  //the items array is initialized to an empty array '[]', and 'setItems' is a function for when
  //you want to update the items array
  const [items, setItems] = useState([]);
  const [results, setResults] = useState([]);

  //use this 'useEffect' when you only want something to happen the first time the page is rendered only
  useEffect(() => {
    getItems();
  }, []);

  return (
    //a flatlist takes in an array as 'data', and a function 'renderItem' tells it what to render for each
    //element in the array.
    <FlatList
      data={items}
      //refreshing and onRefresh let the user swipe down to refresh the page.
      refreshing={loading}
      onRefresh={() => {
        setLoading(true);
        getItems();
      }}
      renderItem={({item}) => {
        return (
          //ItemCard is a component I made in /src/components, so that
          //any time we want to edit the way an item is shown, it will
          //do so on every page that we want an ItemCard on.
          <ItemCard
            item={item}
            //I am passing a custom parameter "buttons" so that we can have different buttons
            //each time that the ItemCard is used.
            //On this page, we want the reserve button, but on the ReserveItem page,
            //we do not need any buttons.
            buttons={
              <Button
                //this is how to pass an object to the next page. Take a look at
                //ReserveRoom.js to see how the item is used there.
                onPress={() => {
                  navigation.navigate('ReserveItem', {item});
                }}>
                Reserve
              </Button>
            }
          />
        );
      }}
    />
  );
};

export default BrowseAllItems;
