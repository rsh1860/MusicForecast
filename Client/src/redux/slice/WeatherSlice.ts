import { createSlice } from '@reduxjs/toolkit';

const WeatherSlice = createSlice({
  name: 'Weather',
  initialState: {
    value: '',
    Result: [],
  },
  reducers: {
    weatherInfo: (state, action) => {
      state.value = action.payload;
    },
    changeWeather: (state, action) => {
      state.value = action.payload;
    },
    weatherResult: (state, action) => {
      state.Result = action.payload;
    },
  },
});

export default WeatherSlice;

export const { weatherInfo, changeWeather, weatherResult } = WeatherSlice.actions;
