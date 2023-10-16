import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { closeSongLists } from '../../redux/slice/ModalSlice';
import { playlistData, playlistDetail, setPlaylistTitle } from '../../redux/slice/PlaylistsSlice';
import { RootState } from '../../redux/store';
import backbtn from '../../assets/images/backbtn.png';
import PlaylistUpdateBtn from './Button/PlaylistUpdateBtn';
import SongDeleteBtn from './Button/SongDeleteBtn';
import AlbumFirst from '../../assets/images/AlbumFirst.png';
import PlaylistLikeBtn from './Button/PlaylistLiketBtn';
import MyPliAddSongbtn from './Button/MyPliAddSongbtn';

export type SongData = {
  title: string;
  imageUrl: string;
  album: string;
  artistName: string;
  songId: number;
};

export type PlaylistDetail = {
  view: number;
  like: number;
  public: boolean;
  memberId: number;
  playlistId: number;
};

const PlaylistsDetail = () => {
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };

  const dispatch = useDispatch();

  const playlistId = useSelector((state: RootState) => state.playlists.selectedPlaylistId);
  const deletedSongs: number[] = useSelector((state: RootState) => state.songlists.deletedSongs);
  const detailData: PlaylistDetail = useSelector((state: RootState) => state.playlists.detailData);
  const addedSongs: SongData[] = useSelector((state: RootState) => state.playlists.detailInfo);
  const filteredSongs = addedSongs.filter((song) => !deletedSongs.includes(song.songId));
  const title = useSelector((state: RootState) => state.playlists.playlistTitle);
  const memberId = useSelector((state: RootState) => state.login.memberid);
  const memberIdNum = Number(memberId);

  const [isClicked, setIsClicked] = useState<boolean>(false);
  const [isPublic, setIsPublic] = useState<boolean>(detailData.public); // 초기값 공개
  const [selectedPlaylistInfo, setSelectedPlaylistInfo] = useState<SongData | null>(null);

  const handlePlaylistClick = (playlist: SongData) => {
    setSelectedPlaylistInfo(playlist);
  };

  const handleCloseSong = () => {
    dispatch(closeSongLists());
  };

  const getAddedSongs = (): void => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/playlist/${playlistId}`, { headers })
      .then((res) => {
        dispatch(playlistDetail(res.data.data.playlistSongs));
        dispatch(playlistData(res.data.data));
        setIsPublic(res.data.data.public);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    getAddedSongs();
  }, [playlistId]);

  return (
    <>
      <div className="fixed bottom-0 flex justify-center bg-opacity-1 ">
        <div className="w-[60vh] h-[60vh] mt-12 fixed right-8 bottom-40">
          <div className="h-[60vh] flex flex-col justify-center items-center rounded-2xl bg-gradient-to-b from-[#000000f3] to-[#1d2435] shadow-xl text-[#b3b4ca]  ">
            {/* 플레이리스트 상단 */}
            <div className="w-full flex justify-around items-center mt-4">
              <button onClick={handleCloseSong} className="mr-10 mt-8 ">
                <img src={backbtn} className="w-[30px]" />
              </button>
              {/* 검색칸 */}
              <div className="flex mt-8">
                {!isClicked ? (
                  <h1 className="text-xl font-['Anton-Regular']">{title}</h1>
                ) : (
                  <input
                    onChange={(e) => dispatch(setPlaylistTitle(e.target.value))}
                    value={title}
                    type="text"
                    className="w-[20vh] h-[5vh] pl-4 bg-[#444444d0] rounded-3xl border border-gray-500"
                  />
                )}
              </div>
              <div className="flex flex-col mt-8">
                <div className="w-full flex justify-start mb-4">
                  <input
                    type="checkbox"
                    className="mr-2"
                    checked={isPublic}
                    onChange={() => setIsPublic(!isPublic)} // 공개/비공개 체크 상태 토글
                  />
                  <span className="mr-4 text-[#ffff]">공개</span>
                  <input
                    type="checkbox"
                    className="mr-2"
                    checked={!isPublic} // 반대 값으로 설정
                    onChange={() => setIsPublic(!isPublic)} // 공개/비공개 체크 상태 토글
                  />
                  <span>비공개</span>
                </div>
                <div className="flex items-center">
                  <h2 className="text-xs">Views: {detailData.view}</h2>
                  <h2 className="ml-4 text-xs">like: {detailData.like}</h2>
                  <PlaylistLikeBtn playlistId={detailData.playlistId} />
                </div>
              </div>
            </div>
            {/* 노래에 대한 디테일 */}
            <div className="w-full h-[7vh] flex justify-start items-center mt-8 bg-[#302f2f] opacity-80 font-['Anton-Regular']">
              {selectedPlaylistInfo ? (
                <>
                  <img src={selectedPlaylistInfo.imageUrl} className="w-[7vh] h-[5vh] mx-8" />
                  <div className="w-full h-[7vh] flex flex-col justify-center">
                    <p className="my-1 text-xs">Title : {selectedPlaylistInfo.title}</p>
                    <p className="my-1 text-xs">Artist : {selectedPlaylistInfo.artistName}</p>
                  </div>
                </>
              ) : (
                <>
                  <img src={AlbumFirst} className="w-[7vh] h-[5vh] mx-8" />
                  <div className="w-full h-[12vh] flex flex-col justify-center">
                    <p className="my-1 text-xs">Title : </p>
                    <p className="my-1 text-xs">Artist : </p>
                  </div>
                </>
              )}
            </div>
            {/* 플리 앨범, 제목, 내용 */}
            <div className="w-full grid grid-cols-5 text-center my-2 font-['Anton-Regular'] text-xs">
              <h3>No.</h3>
              <h3>Album Image</h3>
              <h3>Title</h3>
              <h3>Artist Name</h3>
              <h3>Edit</h3>
            </div>
            {/* 플리 노래목록 맵핑 */}
            <ul className="w-full h-[70vh] flex flex-col overflow-x-hidden ">
              {filteredSongs.map((selectedSongs, index) => (
                <li
                  onClick={() => handlePlaylistClick(selectedSongs)}
                  className="w-full h-[7vh] grid grid-cols-5 items-center text-center border-t-2 border-solid border-gray-200 border-opacity-20 hover:bg-[#47464680]"
                >
                  {/* No */}
                  <h3 className="">{index + 1}</h3>
                  <div className="flex justify-center items-center h-[7vh]">
                    <img src={selectedSongs.imageUrl} className="w-[30px] h-[30px]" />
                  </div>
                  {/* Title */}
                  <p className="text-xs">{selectedSongs.title}</p>
                  {/* Artist Name */}
                  <p className="text-xs">{selectedSongs.artistName}</p>
                  {isClicked ? <SongDeleteBtn songId={selectedSongs.songId} /> : null}
                </li>
              ))}
            </ul>
            {detailData && (
              <div className="flex justify-center mt-8">
                {memberIdNum === detailData.memberId ? (
                  <PlaylistUpdateBtn
                    isPublic={isPublic}
                    isClicked={isClicked}
                    setIsClicked={setIsClicked}
                  />
                ) : (
                  <MyPliAddSongbtn />
                )}
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default PlaylistsDetail;
