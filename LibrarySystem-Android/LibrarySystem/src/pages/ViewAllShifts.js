import React, {useState} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import axios from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
const baseUrl = 'https://librarysystem-backend-321.herokuapp.com/';
//this is from vue js file
var AXIOS = axios.create({
  baseURL: baseUrl,
});


const ViewAllShifts = () => {
  const [error, setError] = useState('');
  const [theShifts, setShifts] = useState([]); 

  const getShifts = () => {
 
    // Retrieves all shifts with get request and reformats so they are presentable
    AXIOS.get("api/shift/view_all_shifts?currentUserId=admin").then(res => {
      let shifts = res.data; 
      
     // E.g. Kevin D.: Monday 9:00 - 18:00
     for (var i=0; i<shifts.length; i++) {
        var str = shifts[i].librarianId.split("-"); 
       const newStr = str[0].replace(/[0-9]/g, ''); 
         var libName = newStr; 
  
      //var libName = shifts[i].librarianId; 
       shifts[i] =  (libName + ": "+shifts[i].dayOfWeek[0] + shifts[i].dayOfWeek.slice(1).toLowerCase()
       + ' ' + shifts[i].startTime.substring(0, 5) +' - ' +  shifts[i].endTime.substring(0,5))
       + "\n"; 
     }
     setShifts(shifts ); 
   })
   .catch(e => {
     if (e.response.data.error) {
       setError(e.response.data.error);
     } else {
       setError(e.response.data);
     }
   });
 }
  return (
    <View>
      <Text style={styles.h2} onLayout={getShifts}> {theShifts} </Text>
    </View>
  );
};
const styles = StyleSheet.create({
 
  h2: {
    color: 'black',
    fontSize: 19,
    marginLeft: 10,
  },
});
export default ViewAllShifts;
