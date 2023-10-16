import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../redux/store';
import { setSelectedPlaylistId, setSelectedMemberId } from '../../redux/slice/PlaylistsSlice';
import { openSongLists, closeSearchedModal } from '../../redux/slice/ModalSlice';
import PlaylistsDetail from './PlayListsDetail';
import xbtn from '../../assets/images/xbtn.svg';
import playlistdisc from '../../assets/images/playlistdisc.png';

const SearchedPlaylists = () => {
  const dispatch = useDispatch();

  const searchedPlaylists = useSelector((state: RootState) => state.playlists.searchedPlaylist);
  const isDetailOpen = useSelector((state: RootState) => state.modal.isSongOpen);

  const handleOpenDetail = (playlistId: number, memberId: number) => {
    dispatch(openSongLists());
    dispatch(setSelectedPlaylistId(playlistId));
    dispatch(setSelectedMemberId(memberId));
  };

  return (
    <>
      <div className="w-[600px] h-[670px] fixed bottom-0 flex justify-center z-10000">
        <div className="w-[600px] h-[670px] bg-gradient-to-b from-[#000000f3] to-[#1d2435] mt-12 fixed right-8 bottom-40">
          <div className="h-[670px] flex flex-col justify-center items-center rounded-2xl  shadow-xl text-[#b3b4ca]">
            {/* 플레이리스트 상단 */}
            <div className="flex justify-around">
              <button className="mr-10 mt-8 ">
                <img
                  src={xbtn}
                  onClick={() => dispatch(closeSearchedModal())}
                  className="w-[35px]"
                />
              </button>
              {/* 검색칸 */}
              <div className="flex mt-8">
                <input
                  type="text"
                  placeholder="   플레이리스트 이름을 입력해주세요"
                  className="w-[400px] h-[50px] bg-[#444444d0] rounded-3xl border border-gray-500"
                ></input>
              </div>
              <button className="mt-8"></button>
            </div>
            {/* 플레이리스트 */}
            <div className="w-full h-[50px] flex justify-between items-center mt-8 font-['Anton-Regular']">
              <h1 className="ml-8">Searched Playlists</h1>
            </div>
            {/* 플리 앨범, 제목, 내용 */}
            <ul className="w-[550px] h-[450px] flex flex-wrap overflow-y-scroll">
              {searchedPlaylists.map((el) => (
                <li
                  onClick={() => handleOpenDetail(el.playlistId, el.memberId)}
                  className="w-[100px] h-[150px] relative flex flex-col justify-center items-center text-center hover:translate-y-[-15px] transition duration-300 ease-in-out "
                >
                  {/* 플리 리스트들 */}
                  <div className="h-[100px]">
                    <img src={playlistdisc} className="w-[100px] h-[100px] animate-spin-slow" />
                    <h1 className="mt-4 text-xs">{el.title}</h1>
                  </div>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
      {isDetailOpen && <PlaylistsDetail />}
    </>
  );
};

export default SearchedPlaylists;
