import React from 'react';
import {View} from 'react-native';
import {Title} from 'react-native-paper';
import ItemCard from '../components/ItemCard';

//In BrowseAllItems.js we passed in an "item" object as  a parameter.
//this can be accessed on this page as route.params.item.
//We can also later use the 'navigation' that we imported to navigate to another page,
//just as we did in BrowseAllItems.js to get here.
const ReserveItem = ({route, navigation}) => {
  return (
    <View>
      <ItemCard item={route.params.item} />
    </View>
  );
};

export default ReserveItem;
