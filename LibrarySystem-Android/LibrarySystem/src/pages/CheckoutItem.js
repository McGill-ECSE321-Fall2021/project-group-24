import React, {useState} from 'react';
import {View, Image, Text} from 'react-native';
import {Card, Title, Paragraph, ActivityIndicator} from 'react-native-paper';
import ItemCard from '../components/ItemCard';

const CheckoutItem = ({item, buttons}) => {
  return <ItemCard item={item} buttons={buttons} />;
};

export default CheckoutItem;
