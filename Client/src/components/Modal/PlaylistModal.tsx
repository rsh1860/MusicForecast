import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import axios from 'axios';
import {
  closeModal,
  openDetailModal,
  openMyShowAll,
  openSongLists,
} from '../../redux/slice/ModalSlice';
import {
  setSelectedPlaylistId,
  playlistInfo,
  myPlaylist,
  setPlaylistTitle,
} from '../../redux/slice/PlaylistsSlice';
import { RootState } from '../../redux/store';
import xbtn from '../../assets/images/xbtn.svg';
import PlaylistsShowAll from '../Playlist/PlaylistsShowAll';
import PlaylistsDetail from '../Playlist/PlayListsDetail';
import MyPlaylistsShowAll from '../Playlist/MyPlaylistsShowAll';
import playlistdisc from '../../assets/images/playlistdisc.png';
import PlaylistsSearch from '../Playlist/PlaylistsSearch';
import SearchedPlaylists from '../Playlist/SearchedPlaylists';

export type PlaylistInfo = {
  title: string;
  playlistId: number;
};

export type SongData = {
  title: string;
  imageUrl: string;
  album: string;
  artistName: string;
  songId: number;
};

export type PlaylistData = {
  title: string;
  views: number;
  playlistId: number;
  memberId: number;
  playlistSongs: [];
  playlistTags: [];
};

const PlaylistModal = () => {
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };
  const token = useSelector((state: RootState) => state.login.accessToken);
  const dispatch = useDispatch();
  const isSearchedOpen = useSelector((state: RootState) => state.modal.isSearchedOpen);
  const isDetailOpen = useSelector((state: RootState) => state.modal.isSongOpen);
  const isShowAll = useSelector((state: RootState) => state.modal.isDetailOpen);
  const isMyShowAll = useSelector((state: RootState) => state.modal.isMyShowAll);
  const playlistsInfo: PlaylistInfo[] = useSelector((state: RootState) => state.playlists.value);
  const myPlaylistsInfo: PlaylistInfo[] = useSelector(
    (state: RootState) => state.playlists.myPlaylist
  );

  const handleOpenDetail = () => {
    dispatch(openDetailModal());
  };

  const handleMyShowAll = () => {
    dispatch(openMyShowAll());
  };

  const handleCloseModal = () => {
    dispatch(closeModal());
  };

  const handleOpenListDetail = (playlistId: number, title: string) => {
    dispatch(openSongLists());
    dispatch(setSelectedPlaylistId(playlistId));
    dispatch(setPlaylistTitle(title));
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

  // 마이 플리 받아오기
  useEffect(() => {
    getPlaylists();
  }, []);

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

  // 플레이리스트 받아오기
  useEffect(() => {
    getRecommendPlaylists();
  }, []);

  return (
    <>
      <div className="fixed bottom-0 flex justify-center bg-opacity-1">
        <div className="w-[60vh] h-[60vh] mt-12 animate-fadeInBottomRight-fast fixed right-8 bottom-40">
          <div className="h-[60vh] flex flex-col justify-center items-center rounded-2xl bg-gradient-to-b from-[#000000f3] to-[#1d2435] shadow-xl text-[#b3b4ca] animate-scale-anim  ">
            {/* 플레이리스트 상단 */}
            <div className="flex justify-around mt-9">
              <button onClick={handleCloseModal} className="mr-10">
                <img src={xbtn} className="w-[30px] mt-9" />
              </button>
              {/* 검색칸 */}
              <PlaylistsSearch />
            </div>
            {/* 플레이리스트 */}
            <div className="w-full h-[50px] flex justify-between items-center mt-8 font-['Anton-Regular']">
              <h1 className="ml-6">My Playlists</h1>
              <button onClick={handleMyShowAll} className="mr-12">
                더보기
              </button>
            </div>
            {/* 플리 앨범, 제목, 내용 */}
            <ul className="w-[55vh] mt-6 flex">
              {myPlaylistsInfo.map((myEl, index) => {
                if (index <= 4) {
                  return (
                    <li
                      onClick={() => handleOpenListDetail(myEl.playlistId, myEl.title)}
                      className="h-[15vh] w-[12vh] mb-2 flex justify-start items-center text-center hover:translate-y-[-15px] transition duration-300 ease-in-out"
                    >
                      <div className=" cursor-pointer w-[9vh] h-[9vh] ">
                        <img src={playlistdisc} className="animate-spin-slow" />
                        <h1 className="mt-4 text-xs">{myEl.title}</h1>
                      </div>
                    </li>
                  );
                }
                return <></>;
              })}
            </ul>
            {/* 마이 플레이리스트 */}
            <div className="w-full h-[50px] flex justify-between items-center mt-12 font-['Anton-Regular']">
              <h1 className="ml-6">Recommend</h1>
              <button onClick={handleOpenDetail} className="mr-12">
                더보기
              </button>
            </div>
            {/* 플리 앨범, 제목, 내용 */}
            <ul className="w-[55vh] mt-2 flex justify-center">
              {playlistsInfo.map((el, index) => {
                if (index <= 4) {
                  return (
                    <li
                      onClick={() => handleOpenListDetail(el.playlistId, el.title)}
                      className="h-[15vh] w-[12vh] mb-4 flex justify-start items-center text-center hover:translate-y-[-15px] transition duration-300 ease-in-out"
                    >
                      <div className=" cursor-pointer w-[9vh] h-[9vh]">
                        <img src={playlistdisc} className="animate-spin-slow" />
                        <h1 className="mt-4 text-xs">{el.title}</h1>
                      </div>
                    </li>
                  );
                }
                return <></>;
              })}
            </ul>
            <div className="w-[150px] h-[30px] mb-4 mt-8"></div>
          </div>
        </div>
      </div>
      {isSearchedOpen && ( // 모달이 열렸을 때만 모달 컴포넌트 렌더링
        <SearchedPlaylists />
      )}
      {isDetailOpen && <PlaylistsDetail />}
      {isShowAll && <PlaylistsShowAll />}
      {isMyShowAll && <MyPlaylistsShowAll />}
    </>
  );
};

export default PlaylistModal;
