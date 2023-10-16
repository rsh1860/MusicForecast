import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { closeSongAddModal } from '../../redux/slice/ModalSlice';
import { RootState } from '../../redux/store';

export type PlaylistInfo = {
  title: string;
  playlistId: number;
};

export type UserIntro = {
  image: string;
  intro: string;
  memberId: number;
  nickname: string;
};

export type SongInfo = {
  songId: number;
  title: string;
  artistName: string;
  albumName: string;
  imageUrl: string;
};

const SongAddModal = () => {
  const dispatch = useDispatch();
  const [userIntro, setUserIntro] = useState<UserIntro | undefined>();
  const myPlaylistsInfo: PlaylistInfo[] = useSelector(
    (state: RootState) => state.playlists.myPlaylist
  );
  const accessToken = useSelector((state: RootState) => state.login.accessToken);

  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
    'Authorization': accessToken,
  };

  const token = useSelector((state: RootState) => state.login.accessToken);

  // SongLists 컴포넌트에서 선택한 노래 목록 가져오기
  const selectedSongs = useSelector((state: RootState) => state.songlists.songInfoList);

  const handleCloseAddSong = () => {
    dispatch(closeSongAddModal());
  };
  // 유저 닉네임 받아오기
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/members/my_page/intro`, { headers })
      .then((res) => {
        setUserIntro(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  // 노래 가져오기
  const handleAddSong = async (plyId: number) => {
    let addedSongsCount = 0;

    const axiosRequests = selectedSongs.map(async (song) => {
      try {
        const res = await axios.post(
          `${process.env.REACT_APP_BE_API_URL}/song/${song.songId}/${plyId}/add`,
          {},
          {
            headers: {
              Authorization: token,
            },
          }
        );
        addedSongsCount += 1;
        console.log(res);
      } catch (err) {
        console.error(err);
      }
    });
    await Promise.all(axiosRequests);

    if (addedSongsCount > 0) {
      alert(`${addedSongsCount}개의 노래가 추가되었습니다.`); // 한 번의 alert 메시지 표시
    }
  };

  return (
    <div className="font-['Anton-Regular']">
      <div className="w-[28vh] h-[24vh] flex flex-col  items-center fixed left-8 bottom-0 bg-gradient-to-b from-[#97c7f3] to-[#fafafa7c] shadow-lg rounded-xl animate-fadeIn">
        <div className="flex items-center mt-4 ml-4">
          {userIntro && <p className="text-sm">{userIntro.nickname}님의 플레이리스트</p>}
          <button
            onClick={handleCloseAddSong}
            className="w-[30px] h-[30px] ml-4 inline-flex justify-center items-center rounded-2xl border-2 text-black text-center border-sky-400 bg-white hover:bg-[#85b5db] hover:text-white"
          >
            X
          </button>
        </div>
        {myPlaylistsInfo.map((el) => {
          return (
            <li
              className="w-[200px] mt-4 bg-[#dceef1] flex justify-center items-center text-center hover:translate-y-[-15px] transition duration-300 ease-in-out cursor-pointer rounded-2xl shadow-lg"
              onClick={() => handleAddSong(el.playlistId)}
            >
              <h1>{el.title}</h1>
            </li>
          );
        })}
      </div>
    </div>
  );
};

export default SongAddModal;
