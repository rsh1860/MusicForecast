import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import axios from 'axios';
import { RootState } from '../../../redux/store';
import { weatherResult } from '../../../redux/slice/WeatherSlice';
import { openModal, openSongLists } from '../../../redux/slice/ModalSlice';
import {
  setSelectedPlaylistId,
  setPlaylistTitle,
  playlistDetail,
} from '../../../redux/slice/PlaylistsSlice';
import playlistdisc from '../../../assets/images/playlistdisc.png';

const RecommendLists = () => {
  const dispatch = useDispatch();
  const weather = useSelector((state: RootState) => state.weather.value);
  const RecommendResult = useSelector((state: RootState) => state.weather.Result);

  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };

  const handleDetailOpen = async (playlistId: number, title: string) => {
    dispatch(openModal());

    setTimeout(async () => {
      await axios
        .get(`${process.env.REACT_APP_BE_API_URL}/playlist/${playlistId}`, { headers })
        .then((res) => {
          dispatch(playlistDetail(res.data.data.playlistSongs));
        })
        .catch((err) => {
          console.log(err);
        });
      dispatch(openSongLists());
      dispatch(setSelectedPlaylistId(playlistId));
      dispatch(setPlaylistTitle(title));
    }, 1500);
  };

  // 날씨별 추천 리스트 가져오기
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/weather/result?page=1&size=10&q=${weather}`, {
        headers,
      })
      .then((res) => {
        dispatch(weatherResult(res.data.data));
        console.log(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [weather]);

  return (
    <>
      <div className="w-full flex flex-col justify-center items-center">
        <h1 className="my-8 font-['Anton-Regular'] text-2xl">RecommendLists</h1>
        <div className="flex ">
          {RecommendResult.map((el) => (
            <div
              onClick={() => handleDetailOpen(el.playlistId, el.title)}
              className="flex flex-col justify-center items-center w-[16vh] bg-[#d8d5d5] bg-opacity-10 shadow-xl rounded-xl backdrop-blur-md mx-4 hover:translate-y-[-15px] transition duration-300 ease-in-out cursor-pointer"
            >
              <img src={playlistdisc} className="animate-spin-slow w-[10vh] h-[10vh] my-4" />
              <p className="h-[7vh] font-bold text-lg text-center">{el.title}</p>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default RecommendLists;
