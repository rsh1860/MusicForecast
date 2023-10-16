import axios from 'axios';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';

export type PlaylistLikeBtnProps = {
  playlistId: number;
};

const PlaylistLikeBtn = ({ playlistId }: PlaylistLikeBtnProps) => {
  const [isLiked, setIsLiked] = useState(false);
  const token = useSelector((state: RootState) => state.login.accessToken);

  const handleLike = () => {
    axios
      .patch(
        `${process.env.REACT_APP_BE_API_URL}/playlist/${playlistId}/like`,
        {},
        {
          headers: {
            'Authorization': token,
            'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
          },
        }
      )
      .then((res) => {
        console.log(res);
        setIsLiked(true);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      {isLiked ? (
        <button onClick={handleLike} className="ml-4 text-lg text-center text-red-500">
          ♥️
        </button>
      ) : (
        <button onClick={handleLike} className="ml-4 text-lg text-center text-gray-500">
          ♥️
        </button>
      )}
    </>
  );
};

export default PlaylistLikeBtn;
