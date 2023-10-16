import { useSelector, useDispatch } from 'react-redux';
import axios from 'axios';
import { RootState } from '../../../redux/store';
import { myPlaylist, playlistInfo } from '../../../redux/slice/PlaylistsSlice';

export type PublicProp = {
  isPublic: boolean;
  isClicked: boolean;
  setIsClicked: any;
};

const PlaylistUpdateBtn = ({ isPublic, setIsClicked, isClicked }: PublicProp) => {
  const dispatch = useDispatch();
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };
  const playlistId = useSelector((state: RootState) => state.playlists.selectedPlaylistId);
  const token = useSelector((state: RootState) => state.login.accessToken);
  const title = useSelector((state: RootState) => state.playlists.playlistTitle);

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

  const handleUpdate = () => {
    axios
      .patch(
        `${process.env.REACT_APP_BE_API_URL}/playlist/${playlistId}`,
        { title, public: isPublic, tag: [] },
        {
          headers: {
            'Authorization': token,
            'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
          },
        }
      )
      .then((res) => {
        getPlaylists();
        getRecommendPlaylists();
        setIsClicked(false);
        console.log('수정 성공 !', res);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      {isClicked ? (
        <button
          onClick={handleUpdate}
          className="w-[15vh] h-[5vh] mb-4 mr-4 rounded-2xl border-2 border-purple-400 hover:bg-[#9574b1] hover:text-white"
        >
          완료
        </button>
      ) : (
        <button
          onClick={() => setIsClicked(true)}
          className="w-[15vh] h-[5vh] mb-4 mr-4 rounded-2xl border-2 border-purple-400 hover:bg-[#9574b1] hover:text-white"
        >
          수정하기
        </button>
      )}
    </>
  );
};

export default PlaylistUpdateBtn;
