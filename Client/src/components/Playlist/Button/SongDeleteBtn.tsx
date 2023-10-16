import axios from 'axios';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../../redux/store';
import { deletedSongs } from '../../../redux/slice/SonglistsSlice';

export type deleteProps = {
  songId: number;
};

const SongDeleteBtn = ({ songId }: deleteProps) => {
  const dispatch = useDispatch();

  const token = useSelector((state: RootState) => state.login.accessToken);
  const playlistId = useSelector((state: RootState) => state.playlists.selectedPlaylistId);
  // 노래 삭제
  const handleSongDelete = () => {
    const shouldDelete = window.confirm('정말 삭제하시겠습니까?');

    if (shouldDelete) {
      return axios
        .patch(
          `${process.env.REACT_APP_BE_API_URL}/song/${playlistId}/${songId}/delete`,
          {},
          {
            headers: {
              Authorization: token,
            },
          }
        )
        .then((res) => {
          dispatch(deletedSongs(songId));
          console.log('삭제 되었습니다.', res);
        })
        .catch((err) => {
          console.log(err);
        });
    }
    return <></>;
  };

  return (
    <>
      <button
        onClick={handleSongDelete}
        className="w-[75px] h-[30px] my-4 ml-7 rounded-2xl border-2 border-purple-400 hover:bg-[#9574b1] hover:text-white"
      >
        삭제하기
      </button>
    </>
  );
};

export default SongDeleteBtn;
