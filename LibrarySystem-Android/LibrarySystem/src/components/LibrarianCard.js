import React from 'react';
import {View, Image} from 'react-native';
import {Button, Card, Title, Paragraph, Text} from 'react-native-paper';

// Author: Arman
// The card for each librarian has their name, address, & email, as well as a button to view their shifts
const LibrarianCard = ({librarian, Dbutton, someShiftsButton}) => {
  return (
    <Card
      style={{
        alignSelf: 'center',
        width: '90%',
        borderRadius: 15,
        elevation: 2,
        marginVertical: 10,
      }}>
      <Card.Content style={{alignItems: 'center'}}>
        <Title>{librarian.firstName}</Title>
        <Paragraph>First Name: {librarian.firstName}</Paragraph>
        <Paragraph>Last Name: {librarian.lastName}</Paragraph>
        <Paragraph>Address: {librarian.address}</Paragraph>
        <Paragraph>Email: {librarian.email}</Paragraph>
      </Card.Content>      
      <Card.Actions style={{alignSelf: 'center'}}>{someShiftsButton}</Card.Actions>

    </Card>
  );
};

export default LibrarianCard;
