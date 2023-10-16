import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import axios from 'axios';
import { closeDetailModal } from '../../redux/slice/ModalSlice';
import { playlistInfo } from '../../redux/slice/PlaylistsSlice';
import xbtn from '../../assets/images/xbtn.svg';
import PlaylistsDetail from './PlayListsDetail';
import Playlists from './Playlists';
import { RootState } from '../../redux/store';
import PlaylistsSearch from './PlaylistsSearch';
import SearchedPlaylists from './SearchedPlaylists';

export type PlaylistInfo = {
  title: string;
  playlistId: number;
  memberId: number;
};

const PlaylistsShowAll = () => {
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };
  const dispatch = useDispatch();

  const isDetailOpen = useSelector((state: RootState) => state.modal.isSongOpen);
  const isSearchedOpen = useSelector((state: RootState) => state.modal.isSearchedOpen);
  const playlistsInfo: PlaylistInfo[] = useSelector((state: RootState) => state.playlists.value);

  const handleCloseModal = () => {
    dispatch(closeDetailModal());
  };

  // 플레이리스트 받아오기
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/playlist?page=1&size=80`, { headers })
      .then((res) => {
        dispatch(playlistInfo(res.data.data));
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <div className="fixed bottom-0 flex justify-center bg-opacity-1 ">
        <div className="w-[60vh] h-[60vh] mt-12 fixed right-8 bottom-40">
          <div className="h-[60vh] flex flex-col justify-center items-center rounded-2xl bg-gradient-to-b from-[#000000f3] to-[#1d2435] shadow-xl text-[#b3b4ca]">
            {/* 플레이리스트 상단 */}
            <div className="flex justify-around mb-8">
              <button onClick={handleCloseModal} className="mr-10 mt-8 ">
                <img src={xbtn} className="w-[30px]" />
              </button>
              {/* 검색칸 */}
              <div className="flex items-center ">
                <PlaylistsSearch />
              </div>
            </div>
            {/* 플레이리스트 */}
            <div className="w-full h-[50px] flex justify-between items-center mt-2 font-['Anton-Regular']">
              <h1 className="ml-8">Recommend</h1>
            </div>
            {/* 플리 앨범, 제목, 내용 */}
            <ul className="w-[55vh] h-[45vh] mb-6 flex flex-wrap overflow-y-scroll">
              {playlistsInfo.map((el) => (
                <Playlists el={el} playlistId={el.playlistId} memberId={el.memberId} />
              ))}
            </ul>
          </div>
        </div>
      </div>
      {isDetailOpen && <PlaylistsDetail />}
      {isSearchedOpen && ( // 모달이 열렸을 때만 모달 컴포넌트 렌더링
        <SearchedPlaylists />
      )}
    </>
  );
};

export default PlaylistsShowAll;
