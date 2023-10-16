import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import Today from '../../assets/images/todaymusic.png';
import RecommendLists from './Genre/RecommendLists';
import Weather from './Weather';
import WeatherChange from './Button/WeatherChange';

export type WeatherData = {
  weatherDescription: string;
};

const ContentBox = () => {
  const weather = useSelector((state: RootState) => state.weather.value);

  const SunBackground =
    'https://user-images.githubusercontent.com/101685650/268509123-363c9802-07a3-4c35-85fe-31182b48eb78.gif';
  const RainyBackground =
    'https://user-images.githubusercontent.com/101685650/268509143-d480188e-ac76-4be5-b8f0-433fe91aae0a.gif';
  const SnowBackground =
    'https://user-images.githubusercontent.com/101685650/268509137-d5720ad4-7a3e-417a-b098-23ab5d747897.gif';
  const CloudyBackground =
    'https://user-images.githubusercontent.com/101685650/268509096-5fc53919-dc9e-415f-a35b-aec99f9a6d61.gif';

  let backgroundImage = SunBackground; // 기본 이미지로 설정

  // 날씨에 따른 배경화면 처리
  if (weather === 'Clear') {
    backgroundImage = SunBackground;
  } else if (weather === 'Rain') {
    backgroundImage = RainyBackground;
  } else if (weather === 'Snow') {
    backgroundImage = SnowBackground;
  } else if (weather === 'Clouds') {
    backgroundImage = CloudyBackground;
  }

  return (
    <div
      className="w-[1230px] h-[70vh] bg-[#3c84d633] bg-opacity-5 shadow-2xl rounded-xl"
      style={{
        backgroundImage: `url(${backgroundImage})`, // 변경된 이미지 경로를 설정
        backgroundPosition: 'center',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
      }}
    >
      <div className="w-full flex justify-between items-center mt-12">
        <WeatherChange />
        <Weather />
      </div>
      <div className="flex justify-center items-center">
        <img src={Today} alt="TodayMusic" className="w-[33vh] h-[13vh] mt-12" />
      </div>
      <RecommendLists />
    </div>
  );
};

export default ContentBox;
