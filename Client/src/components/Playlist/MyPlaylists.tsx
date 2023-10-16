import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { openSongLists } from '../../redux/slice/ModalSlice';
import {
  setSelectedMemberId,
  setSelectedPlaylistId,
  myPlaylist,
  playlistInfo,
} from '../../redux/slice/PlaylistsSlice';
import playlistdisc from '../../assets/images/playlistdisc.png';
import { PlaylistInfo } from './PlaylistsShowAll';
import { RootState } from '../../redux/store';

type PlaylistProps = {
  el: PlaylistInfo;
};

const MyPlaylists = ({ el }: PlaylistProps) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  // 삭제 버튼 클릭시 opacity 변경을 위한 상태
  const [isHovered, setIsHovered] = useState(false);

  const token = useSelector((state: RootState) => state.login.accessToken);
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
    'Authorization': token,
  };

  const handleOpenDetail = () => {
    dispatch(openSongLists());
    dispatch(setSelectedPlaylistId(el.playlistId));
    dispatch(setSelectedMemberId(el.memberId));
  };

  const getPlaylists = (): void => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/playlist/my?page=1&size=10`, {
        headers: {
          'Authorization': token,
          'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
        },
      })
      .then((res) => {
        dispatch(myPlaylist(res.data.data));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const getRecommendPlaylists = (): void => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/playlist`, {
        headers,
      })
      .then((res) => {
        getPlaylists();
        dispatch(playlistInfo(res.data.data));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleListDelete = () => {
    const shouldDelete = window.confirm('정말 삭제하시겠습니까?');

    if (shouldDelete) {
      return axios
        .delete(`${process.env.REACT_APP_BE_API_URL}/playlist/${el.playlistId}`, {
          headers: {
            'Authorization': token,
            'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
          },
        })
        .then((res) => {
          console.log(res);
          alert('플리가 삭제되었습니다.');
          getPlaylists();
          getRecommendPlaylists();
        })
        .catch((err) => {
          if (err.response.status === 500) {
            navigate('/login');
          }
          console.log(err);
        });
    }
    return <></>;
  };

  return (
    <>
      <li
        className="w-[12vh] h-[15vh] relative flex flex-col justify-center items-center text-center hover:translate-y-[-15px] transition duration-300 ease-in-out cursor-pointer"
        onMouseEnter={() => setIsHovered(true)} // 호버 상태에 들어갈 때
        onMouseLeave={() => setIsHovered(false)} // 호버 상태에서 나올 때
      >
        <button
          onClick={handleListDelete}
          className={`w-[25px] h-[25px] flex justify-center text-center items-center relative top-5 -right-14 bg-[#fa1f1f81] text-white text-lg rounded-full ${
            isHovered ? 'opacity-100' : 'opacity-0' // 상태에 따라 opacity 변경
          }`}
          style={{ transition: 'opacity 0.3s' }}
        >
          X
        </button>
        {/* 플리 리스트들 */}
        <div onClick={handleOpenDetail} className="w-[9vh] h-[9vh] flex flex-col items-center">
          <img src={playlistdisc} className="animate-spin-slow" />
          <h1 className="mt-4 text-xs">{el.title}</h1>
        </div>
      </li>
    </>
  );
};
export default MyPlaylists;
