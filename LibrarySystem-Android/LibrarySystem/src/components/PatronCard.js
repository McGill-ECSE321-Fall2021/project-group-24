import React from 'react';
import {View, Image} from 'react-native';
import {Button, Card, Title, Paragraph, Text} from 'react-native-paper';

const PatronCard = ({patron, Dbutton, Vbutton}) => {
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
        <Title>{patron.firstName}</Title>
        <Paragraph>First Name: {patron.firstName}</Paragraph>
        <Paragraph>Last Name: {patron.lastName}</Paragraph>
        <Paragraph>ID number: {patron.idNum}</Paragraph>
        <Paragraph>is verified: {patron.verified.toString()}</Paragraph>
        <Paragraph>is resident: {patron.resident.toString()}</Paragraph>
        <Paragraph>
          is registered online: {patron.registeredOnline.toString()}
        </Paragraph>
      </Card.Content>
      <Card.Actions style={{alignSelf: 'center'}}>{Vbutton}</Card.Actions>
      <Card.Actions style={{alignSelf: 'center'}}>{Dbutton}</Card.Actions>
    </Card>
  );
};

export default PatronCard;
