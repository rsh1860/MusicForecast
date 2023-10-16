import axios from 'axios';
import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { weatherInfo } from '../../redux/slice/WeatherSlice';

export type WeatherData = {
  temperature: number;
  weatherDescription: string;
};

const Weather = () => {
  const dispatch = useDispatch();

  const [weather, setWeather] = useState<WeatherData>({
    temperature: 0,
    weatherDescription: '',
  });

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/weather/data`)
      .then(({ data }) => {
        const { temperature, weatherDescription } = data;
        setWeather({ temperature, weatherDescription });
        dispatch(weatherInfo(data.weatherDescription));
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  // 날씨별 이모티콘 처리
  const getWeatherEmoji = (weatherDescription: string) => {
    switch (weatherDescription) {
      case 'Clear':
        return '🌞';
      case 'Clouds':
      case 'Fog':
      case 'Haze':
        return '☁️';
      case 'Rain':
      case 'Drizzle':
        return '🌧️';
      case 'Snow':
        return '☃️';
      case 'Thunderstorm':
        return '⛈️';
      default:
        return '';
    }
  };

  return (
    <div className="flex justify-end text-2xl mr-12">
      <h1 className="mx-8">{getWeatherEmoji(weather.weatherDescription)}</h1>
      <h1>{weather.temperature}℃</h1>
    </div>
  );
};

export default Weather;
