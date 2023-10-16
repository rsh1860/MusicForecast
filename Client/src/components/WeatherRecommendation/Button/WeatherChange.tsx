import { useDispatch } from 'react-redux';
import { changeWeather } from '../../../redux/slice/WeatherSlice';

const WeatherChange = () => {
  const dispatch = useDispatch();

  // 날씨 변경 버튼
  const changeToClearWeather = () => {
    dispatch(changeWeather('Clear'));
  };

  const changeToRainWeather = () => {
    dispatch(changeWeather('Rain'));
  };

  const changeToSnowWeather = () => {
    dispatch(changeWeather('Snow'));
  };

  const changeToCloudyWeather = () => {
    dispatch(changeWeather('Clouds'));
  };

  return (
    <>
      <h1 className="ml-10 font-['Anton-Regular']">WeatherRecommend</h1>
      <div className="w-[280px] flex justify-around">
        <button
          className="w-[60px] h-[40px] rounded-2xl bg-[#d8dee283] text-2xl hover:bg-[#e1be6c] hover:translate-y-[-10px] transition duration-300 ease-in-out"
          onClick={changeToClearWeather}
        >
          🌞
        </button>
        <button
          className="w-[60px] h-[40px] rounded-2xl bg-[#d8dee283] text-2xl hover:bg-[#6574be] hover:translate-y-[-10px] transition duration-300 ease-in-out"
          onClick={changeToRainWeather}
        >
          🌧️
        </button>
        <button
          className="w-[60px] h-[40px] rounded-2xl bg-[#d8dee283] text-2xl hover:bg-[#99c8e4] hover:translate-y-[-10px] transition duration-300 ease-in-out"
          onClick={changeToSnowWeather}
        >
          ☃️
        </button>
        <button
          className="w-[60px] h-[40px] rounded-2xl bg-[#d8dee283] text-2xl hover:bg-[#444] hover:translate-y-[-10px] transition duration-300 ease-in-out"
          onClick={changeToCloudyWeather}
        >
          ☁️
        </button>
      </div>
    </>
  );
};

export default WeatherChange;
