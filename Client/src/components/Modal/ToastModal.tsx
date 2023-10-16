import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import { toast } from 'react-toastify';
import { closeToastModal } from '../../redux/slice/ModalSlice';
import { myPlaylist } from '../../redux/slice/PlaylistsSlice';
import { RootState } from '../../redux/store';

const ToastModal = () => {
  const [title, setTitle] = useState('');
  const [isPublic, setIsPublic] = useState(true); // 초기값 공개

  const dispatch = useDispatch();

  const token = useSelector((state: RootState) => state.login.accessToken);

  const handleCloseToast = () => {
    dispatch(closeToastModal());
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

  const handleAddPlaylist = () => {
    axios
      .post(
        `${process.env.REACT_APP_BE_API_URL}/playlist`,
        { title, public: isPublic },
        {
          headers: {
            'Authorization': token,
            'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
          },
        }
      )
      .then((res) => {
        console.log(res);
        toast.success('플리가 추가되었습니다.');
        getPlaylists();
        dispatch(closeToastModal());
      })

      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className="fixed bottom-72 right-48 flex flex-col justify-center items-center font-['Anton-Regular'] bg-[#4a4b4a42] text-[#838282]">
      <div className="w-[30vh] flex flex-col justify-center items-center bg-[#414052] border-2 border-gray-500 border-solid animate-fadeIn">
        <div className="my-8 text-[#ffff]">
          <p className="mb-4">플리 제목</p>
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            type="text"
            className="w-[25vh] pl-2 rounded-2xl bg-[#6b6767]"
          ></input>
        </div>
        <div className="w-full flex justify-start ml-12 mb-4">
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
        <div>
          <button
            onClick={handleAddPlaylist}
            className="w-[70px] h-[40px] mb-4 ml-4 rounded-2xl border-2 border-purple-400 hover:bg-[#9574b1] hover:text-white"
          >
            추가
          </button>
          <button
            onClick={handleCloseToast}
            className="w-[70px] h-[40px] mb-4 ml-4 rounded-2xl border-2 border-purple-400 hover:bg-[#9574b1] hover:text-white"
          >
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default ToastModal;
