import { useDispatch } from 'react-redux';
import { openSongLists } from '../../redux/slice/ModalSlice';
import {
  setSelectedMemberId,
  setSelectedPlaylistId,
  setPlaylistTitle,
} from '../../redux/slice/PlaylistsSlice';
import playlistdisc from '../../assets/images/playlistdisc.png';
import { PlaylistInfo } from './PlaylistsShowAll';

type PlaylistProps = {
  el: PlaylistInfo;
  playlistId: number;
  memberId: number;
};

const Playlists = ({ el }: PlaylistProps) => {
  const dispatch = useDispatch();

  const handleOpenDetail = () => {
    dispatch(openSongLists());
    dispatch(setSelectedPlaylistId(el.playlistId));
    dispatch(setSelectedMemberId(el.memberId));
    dispatch(setPlaylistTitle(el.title));
  };

  return (
    <>
      <li className="w-[12vh] h-[15vh] mb-2 relative flex flex-col justify-center items-center text-center hover:translate-y-[-15px] transition duration-300 ease-in-out cursor-pointer">
        {/* 플리 리스트들 */}
        <div onClick={handleOpenDetail} className="w-[9vh] h-[9vh]">
          <img src={playlistdisc} className="animate-spin-slow" />
          <h1 className="mt-4 text-xs">{el.title}</h1>
        </div>
      </li>
    </>
  );
};

export default Playlists;
