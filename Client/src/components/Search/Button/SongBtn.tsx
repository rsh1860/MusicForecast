import { useSelector, useDispatch } from 'react-redux';
import { useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { RootState } from '../../../redux/store';
import SongAddModal from '../../Modal/SongAddModal';
import { openSongAddModal } from '../../../redux/slice/ModalSlice';
import { myPlaylist } from '../../../redux/slice/PlaylistsSlice';

export type PlaylistInfo = {
  title: string;
  playlistId: number;
};

const SongBtn = () => {
  const dispatch = useDispatch();
  const openAddSong = useSelector((state: RootState) => state.modal.isSongAddOpen);
  const token = useSelector((state: RootState) => state.login.accessToken);

  const handleAddSong = () => {
    dispatch(openSongAddModal());
    toast.info('🎤노래 추가를 원하시면 넣으실 해당 플레이리스트를 클릭해주세요!', {
      position: 'bottom-left',
      // className:
    });
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

  useEffect(() => {
    getPlaylists();
  }, []);

  return (
    <>
      <div className="flex justify-center">
        <button
          onClick={() => handleAddSong()}
          className="w-[15vh] h-[5vh] my-6 mr-4 rounded-2xl border-2 border-sky-400 bg-white hover:bg-[#85b5db] hover:text-white"
        >
          추가
        </button>
      </div>

      {openAddSong && <SongAddModal />}
    </>
  );
};

export default SongBtn;
