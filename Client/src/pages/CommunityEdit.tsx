import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';

import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { RootState } from '../redux/store';
// import { playlistInfo } from '../redux/slice/PlaylistsSlice';

import Header from '../components/Header';
import PlaylistIcon from '../components/Playlist/PlaylistIcon';

import plicon from '../assets/images/plicon.png';
import playlistdisc from '../assets/images/turndisk.png';
import turntable from '../assets/images/turntable.png';

export type PlaylistInfo = {
  title: string;
  playlistId: number;
};

interface Post {
  postId: number;
  title: string;
  voteCount: number;
  viewCount: number;
  text: string;
  nickName: string;
  memberId: number;
  comments: [];
  playlistId: number;
}

const CommunityEdit = () => {
  const [posts, setPosts] = useState<Post>({
    postId: 0,
    title: '',
    viewCount: 0,
    voteCount: 0,
    text: '',
    nickName: '',
    memberId: 0,
    comments: [],
    playlistId: 0,
  });
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [selectedPly, setSelectedPly] = useState(posts.playlistId);
  const [checkboxChecked, setCheckboxChecked] = useState(false);

  const navigate = useNavigate();
  const accessToken = useSelector((state: RootState) => state.login.accessToken);
  const memberId = useSelector((state: RootState) => state.login.memberid);
  console.log(memberId);
  console.log(posts);

  const { id } = useParams();

  // id 변수를 숫자로 파싱
  const numericId = parseInt(id, 10);

  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
    'Authorization': accessToken,
  };

  // const dispatch = useDispatch();

  const [playlistsInfo, setPlaylistsInfo] = useState<PlaylistInfo[]>();

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/posts/${numericId}`, {
        headers,
      })
      .then((res) => {
        setPosts(res.data.data);
        setSelectedPly(res.data.data.playlistId);
        console.log(selectedPly);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [numericId]);

  useEffect(() => {
    setTitle(posts.title);
    setContent(posts.text);
  }, [posts]);

  useEffect(() => {
    toast.info(
      '✨우측 하단의 Playlist 버튼을 클릭하여 새로운 Playlist를 생성후, SearchSongs 탭에서 원하는 음악을 검색, 해당 Playlist에 추가하면 게시글에 등록이 가능합니다.',
      {
        autoClose: 8000,
      }
    );
  }, []);

  // 플레이리스트 받아오기
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/playlist/my?page=1&size=10`, {
        headers,
      })
      .then((res) => {
        // dispatch(playlistInfo(res.data.data));
        console.log(res.data.data);
        setPlaylistsInfo(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const handleSelectPly = (a: number) => {
    setSelectedPly(a);
  };

  const handleCancel = () => {
    navigate('../community');
  };

  const handlePost = async () => {
    // 등록 버튼 클릭 시 POST 요청을 보내는 함수
    try {
      const response = await axios.patch(
        `${process.env.REACT_APP_BE_API_URL}/posts/${numericId}`,
        {
          title,
          text: content,
          playlistId: selectedPly,
        },
        {
          headers: {
            'Authorization': accessToken,
            'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
          },
        }
      );

      console.log('서버 응답:', response.data);
      console.log('postId', response.data.data.postId);
      console.log('이름', response.data.data.nickName);

      // POST 요청 성공 후 필요한 작업 수행
      // 예를 들어, 페이지 이동 또는 메시지 표시 등
      navigate(`../community/${response.data.data.postId}`);
    } catch (error) {
      console.error('POST 요청 실패:', error);
      // POST 요청 실패 시 처리할 내용 추가
      alert(error);
    }
  };

  const handleArrowUpClick = () => {
    // ▲를 클릭했을 때의 동작
    const selectedIndex = playlistsInfo.findIndex((el) => el.playlistId === selectedPly);
    if (selectedIndex > 0) {
      setSelectedPly(playlistsInfo[selectedIndex - 1].playlistId);
      setCheckboxChecked(false); // 체크박스 상태 초기화
    }
  };

  const handleArrowDownClick = () => {
    // ▼를 클릭했을 때의 동작
    const selectedIndex = playlistsInfo.findIndex((el) => el.playlistId === selectedPly);
    if (selectedIndex < playlistsInfo.length - 1) {
      setSelectedPly(playlistsInfo[selectedIndex + 1].playlistId);
      setCheckboxChecked(false); // 체크박스 상태 초기화
    }
  };
  return (
    <>
      <div className="w-full h-screen bg-gradient-to-b from-[#D5E5F0] to-[#87c4ed] h-screen">
        <Header />
        <div className="w-full  flex justify-center">
          <div className="w-[98vh] h-[70vh] mt-12 rounded-xl flex flex-col items-center border-2 border-black border-solid shadow-xl bg-gradient-to-b from-[#cfd8dc] to-[#eeee] ">
            <form>
              <div className="flex flex-col justify-center mt-8">
                <div className="w-[80vh] h-[40vh] rounded-xl flex justify-center mt-2 bg-[#444444] items-center border-solid border-black border-4 shadow-2xl">
                  <div className="flex flex-col w-[40vh] items-center ">
                    <input
                      type="text"
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
                      className="w-[40vh] h-[5vh] bg-white shadow-lg rounded-md text-gray-700 pl-5"
                      placeholder="제목을 입력하세요."
                    />

                    <textarea
                      value={content}
                      onChange={(e) => setContent(e.target.value)}
                      className="w-[40vh] h-[28vh] bg-white shadow-lg rounded-md mt-1 text-gray-700 resize-none py-5 px-5 mt-4"
                      placeholder="내용을 입력하세요."
                    />
                  </div>

                  {playlistsInfo ? (
                    <div className="flex flex-col w-[30vh] h-[34.8vh] items-center shadow-lg rounded-md bg-white ml-6 ">
                      <div className="flex flex-col w-[29vh] items-center">
                        <img src={plicon} alt="플레이리스트아이콘" className="mt-2" />
                        <div
                          className="flex justify-center w-[20vh] h-[15vh] rounded-xl relative"
                          style={{
                            backgroundImage: `url('${turntable}')`,
                            backgroundSize: 'cover',
                          }}
                        >
                          <img
                            className="w-[13vh] h-[13vh] animate-spin-slow absolute top-[1.1vh] left-[1.9vh]"
                            src={playlistdisc}
                            alt=""
                          />
                        </div>
                      </div>
                      <div className="overflow-y-scroll h-[12vh]">
                        {playlistsInfo.map((el, idx) => (
                          <div
                            className="h-[5vh] flex flex-row items-center justify-between w-[29vh]"
                            key={idx}
                          >
                            <div className="w-[8vh] h-[5vh] flex justify-end">
                              <input
                                className=" mr-4 inline-flex justify-center items-center"
                                checked={el.playlistId === selectedPly}
                                type="checkbox"
                                onChange={() => {
                                  handleSelectPly(el.playlistId);
                                  setCheckboxChecked(!checkboxChecked); // 체크박스 상태 업데이트
                                }}
                              />
                            </div>
                            <p className="w-[20vh] font-bold">{el.title}</p>
                          </div>
                        ))}
                      </div>
                    </div>
                  ) : (
                    <div className="flex flex-col w-[30vh] h-[34.8vh] items-center justify-center shadow-lg rounded-md bg-white ml-6 ">
                      <h1 className="font-bold text-3xl">Loading......</h1>
                    </div>
                  )}
                </div>
              </div>
            </form>

            {/* MP3 동그라미 */}

            <div className="w-full flex justify-center items-center mt-5">
              <h1 className="text-2xl font-bold mr-2">취소</h1>
              <div className="w-[17vh] h-[17vh] flex flex-col items-center bg-[#9e9e9e] rounded-full border-2 border-gray-500 text-white">
                <button className="w-[4vh] h-[4vh] my-2 hover:translate-y-[5px] transition duration-300 ease-in-out">
                  <p onClick={handleArrowUpClick} className="text-2xl">
                    ▲
                  </p>
                </button>
                <div className="w-full flex justify-between items-center mt-1">
                  <button
                    className="w-[4vh] h-[4vh] ml-2 hover:translate-y-[5px] transition duration-300 ease-in-out"
                    onClick={handleCancel}
                  >
                    <p className="text-2xl">◀</p>
                  </button>
                  <button className="w-[4vh] h-[4vh] rounded-full bg-white"></button>
                  <button
                    className="w-[4vh] h-[4vh] mr-2 hover:translate-y-[5px] transition duration-300 ease-in-out"
                    onClick={handlePost}
                  >
                    <p className="text-2xl">▶</p>
                  </button>
                </div>
                <button className="w-[4vh] h-[4vh] my-2 hover:translate-y-[5px] transition duration-300 ease-in-out">
                  <p onClick={handleArrowDownClick} className="text-2xl">
                    ▼
                  </p>
                </button>
              </div>
              <h1 className="text-2xl font-bold ml-2">등록</h1>
            </div>
          </div>
        </div>
        <PlaylistIcon />
      </div>
    </>
  );
};

export default CommunityEdit;
