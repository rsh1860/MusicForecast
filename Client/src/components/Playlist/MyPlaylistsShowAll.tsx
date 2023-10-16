import { useDispatch, useSelector } from 'react-redux';
import { closeShowAll } from '../../redux/slice/ModalSlice';
import xbtn from '../../assets/images/xbtn.svg';
import PlaylistsDetail from './PlayListsDetail';
import ToastModal from '../Modal/ToastModal';
import { RootState } from '../../redux/store';
import PlaylistAddButton from './Button/PlaylistAddButton';
import MyPlaylists from './MyPlaylists';
import PlaylistsSearch from './PlaylistsSearch';
import SearchedPlaylists from './SearchedPlaylists';

export type PlaylistInfo = {
  title: string;
  playlistId: number;
  memberId: number;
};

const MyPlaylistsShowAll = () => {
  const dispatch = useDispatch();

  const isDetailOpen = useSelector((state: RootState) => state.modal.isSongOpen);
  const isSearchedOpen = useSelector((state: RootState) => state.modal.isSearchedOpen);
  const isOpenToast = useSelector((state: RootState) => state.modal.isToastOpen);
  const myPlaylistsInfo: PlaylistInfo[] = useSelector(
    (state: RootState) => state.playlists.myPlaylist
  );

  const handleCloseModal = () => {
    dispatch(closeShowAll());
  };

  return (
    <>
      <div className="fixed bottom-0 flex justify-center">
        <div className="w-[60vh] h-[60vh] mt-12 fixed right-8 bottom-40">
          <div className="h-[60vh] flex flex-col justify-center items-center rounded-2xl bg-gradient-to-b from-[#000000f3] to-[#1d2435] shadow-xl text-[#b3b4ca]">
            {/* 플레이리스트 상단 */}
            <div className="flex justify-around">
              <button onClick={handleCloseModal} className="mr-10 mt-8 ">
                <img src={xbtn} className="w-[30px]" />
              </button>
              {/* 검색칸 */}
              <div className="flex items-center">
                <PlaylistsSearch />
              </div>
            </div>
            {/* 플레이리스트 */}
            <div className="w-full h-[50px] flex justify-between items-center mt-8 font-['Anton-Regular']">
              <h1 className="ml-8">My Playlists</h1>
            </div>
            {/* 플리 앨범, 제목, 내용 */}
            <ul className="w-[55vh] h-[45vh] flex flex-wrap overflow-y-scroll">
              {myPlaylistsInfo.map((el) => (
                <MyPlaylists el={el} />
              ))}
            </ul>
            <PlaylistAddButton />
          </div>
        </div>
      </div>
      {isDetailOpen && <PlaylistsDetail />}
      {isOpenToast && <ToastModal />}
      {isSearchedOpen && ( // 모달이 열렸을 때만 모달 컴포넌트 렌더링
        <SearchedPlaylists />
      )}
    </>
  );
};

export default MyPlaylistsShowAll;
