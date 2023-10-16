import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
// import playlistimg from '../assets/images/Rectangle 64.png';
import { setCurrentPage } from '../redux/slice/CommunitySlice';
import 'animate.css';
import PlaylistIcon from '../components/Playlist/PlaylistIcon';
import Wrapper from '../components/Wrapper/Wrapper';
// import Motherplayer from '../components/Main/Motherplayer';
import userIcon from '../assets/images/user.png';
import modalBox from '../assets/images/modalbox2-removebg-preview.png';

interface Post {
  postId: number;
  title: string;
  voteCount: number;
  viewCount: number;
  text: string;
  nickName: string;
  memberId: number;
  likeCount: number;
}

const Community = () => {
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };
  const [searchQuery, setSearchQuery] = useState('');
  const [posts, setPosts] = useState<Post[]>([]);
  const navigate = useNavigate();
  const [currentPage, setcurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const dispatch = useDispatch();

  const [modalText, setModalText] = useState('');
  const [modalPosition, setModalPosition] = useState({ top: 0, left: 0 }); // 모달 위치 상태 추가
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleModalOpen = async (
    text: string,
    event: React.MouseEvent<HTMLTableCellElement>,
    idx: number
  ) => {
    const rect = event.currentTarget.getBoundingClientRect();
    await setModalPosition({
      top: event.clientY - rect.top + idx * 50 - 30,
      left: event.clientX - rect.left + 200,
    });
    await setModalText(text);
    await setIsModalOpen(true);
  };

  const handleTitleLeave = () => {
    setModalText('');
    setIsModalOpen(false);
  };

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(event.target.value);
  };

  const handleSearchSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    axios
      .get(
        `${process.env.REACT_APP_BE_API_URL}/posts/search?page=${currentPage}&size=10&keyword=${searchQuery}`,
        { headers }
      )
      .then((res) => {
        setPosts(res.data.data);
        setTotalPages(res.data.pageInfo.totPages);
        dispatch(setCurrentPage(currentPage));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleWriteButton = () => {
    navigate('./write');
  };

  const handlePageChange = (pageNumber: number) => {
    setcurrentPage(pageNumber);
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setcurrentPage(currentPage + 1);
    }
  };

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setcurrentPage(currentPage - 1);
    }
  };
  // 기본 순 나열
  useEffect(() => {
    axios
      .get(
        `${process.env.REACT_APP_BE_API_URL}/posts?page=${currentPage}&size=10&sort=postId,desc`,
        { headers }
      )
      .then((res) => {
        setPosts(res.data.data);
        console.log(res.data.data);
        setTotalPages(res.data.pageInfo.totPages);
        dispatch(setCurrentPage(currentPage));
      })
      .catch((err) => {
        console.log(err);
      });
  }, [currentPage]);

  const handleDefault = () => {
    axios
      .get(
        `${process.env.REACT_APP_BE_API_URL}/posts?page=${currentPage}&size=10&sort=postId,desc`,
        { headers }
      )
      .then((res) => {
        setPosts(res.data.data);
        setTotalPages(res.data.pageInfo.totPages);
        dispatch(setCurrentPage(currentPage));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 좋아요 순 나열
  const handleLikeCount = () => {
    axios
      .get(
        `${process.env.REACT_APP_BE_API_URL}/posts?page=${currentPage}&size=10&sort=likeCount,desc`,
        { headers }
      )
      .then((res) => {
        setPosts(res.data.data);
        setTotalPages(res.data.pageInfo.totPages);
        dispatch(setCurrentPage(currentPage));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 조회 순 나열
  const handleViewCount = () => {
    axios
      .get(
        `${process.env.REACT_APP_BE_API_URL}/posts?page=${currentPage}&size=10&sort=viewCount,desc`,
        { headers }
      )
      .then((res) => {
        setPosts(res.data.data);
        setTotalPages(res.data.pageInfo.totPages);
        dispatch(setCurrentPage(currentPage));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      <div className="bg-gradient-to-b from-[#D5E5F0] to-[#87c4ed] h-screen flex flex-col items-center">
        <div className="w-screen">
          <Header />
        </div>
        <Wrapper>
          <div className="flex flex-col w-[120vh] h-[65vh] items-center bg-gradient-to-b from-[#97c7f3] to-[#fafafa7c] bg-opacity-10 shadow-xl rounded-xl backdrop-blur-xl mt-5">
            <div className="flex w-[120vh] h-[5vh] items-center bg-[#1976d2] rounded-xl justify-center">
              <div className="w-[97vh] flex flex-row text-xl text-white font-bold">
                <p className="w-[5vh]  text-center ">#</p>
                <div className="w-[4vh]"></div>
                <p className="w-[12vh] text-center">작성자</p>
                <p className="w-[76vh]  pl-10">제목</p>
              </div>
              <div className="w-[23vh] flex justify-around">
                <button
                  onClick={handleDefault}
                  className="w-[6.5vh] h-[3vh] bg-[#fff] rounded-xl text-black text-center font-bold text-xs hover:bg-[#0095ff] hover:text-[#ffffff]"
                >
                  기본순
                </button>
                <button
                  onClick={handleViewCount}
                  className="w-[6.5vh] h-[3vh] bg-[#fff] rounded-xl text-black text-center font-bold text-xs hover:bg-[#0095ff] hover:text-[#ffffff]"
                >
                  view
                </button>
                <button
                  onClick={handleLikeCount}
                  className="w-[6.5vh] h-[3vh]  bg-[#fff] rounded-xl text-black text-center font-bold text-xs hover:bg-[#0095ff] hover:text-[#ffffff]"
                >
                  like
                </button>
              </div>
            </div>
            <div className="w-[120vh]">
              <table className="flex flex-col w-[120vh] items-center justify-center ">
                {posts &&
                  posts.map((item, idx) => (
                    <tr
                      className="flex w-[120vh] h-[6vh] rounded-md items-center border-b-[1px] border-solid border-sky-400 justify-center hover:bg-[#b8d8ff]"
                      key={item.postId}
                    >
                      <td className="w-[5vh] text-center text-xs">{idx + currentPage * 10 - 9}</td>
                      <td className="w-[15vh] inline-flex justify-center items-center">
                        <img className="w-[4vh] h-[4vh]" src={userIcon} alt="유저이미지"></img>
                        <p className="text-xs w-[6vh] text-center">
                          <a href={`../othermypage/${item.memberId}`}>{item.nickName}</a>
                        </p>
                      </td>
                      <a href={`./community/${item.postId}`}>
                        <td
                          onMouseEnter={(e) => handleModalOpen(item.text, e, idx)}
                          onMouseLeave={handleTitleLeave}
                          className="flex w-[85vh] text-start pl-10 relative hover:cursor-pointer"
                        >
                          {item.title}
                        </td>
                      </a>
                      <td className="w-[7vh] text-center text-xs">{item.viewCount}</td>
                      <td className="w-[8vh] text-center text-xs">{item.likeCount}</td>
                    </tr>
                  ))}
                {isModalOpen && (
                  <div
                    className="text-center inline-flex items-center justify-center w-[15vh] h-[12vh] absolute "
                    style={{
                      top: modalPosition.top,
                      left: modalPosition.left,
                      backgroundImage: `url('${modalBox}')`, // 배경 이미지 추가
                      backgroundSize: 'cover', // 배경 이미지 크기 설정
                      backgroundRepeat: 'no-repeat', // 배경 이미지 반복 방지
                      backgroundPosition: 'center', // 배경 이미지 위치 설정
                    }}
                  >
                    <p className="text-xs">{modalText}</p>
                  </div>
                )}
              </table>
            </div>
          </div>
          <div className="flex justify-center mt-4 h-[5vh]">
            <div className="w-[60vh]">
              <button
                className="mr-2 px-2 py-1 border rounded"
                onClick={handlePrevPage}
                disabled={currentPage === 1}
              >
                이전
              </button>
              {Array.from({ length: totalPages }, (_, i) => (
                <button
                  key={i}
                  className={`mx-2 px-2 py-1 border rounded ${
                    currentPage === i + 1 ? 'bg-white' : ''
                  }`}
                  onClick={() => handlePageChange(i + 1)}
                >
                  {i + 1}
                </button>
              ))}
              <button
                className="ml-2 px-2 py-1 border rounded"
                onClick={handleNextPage}
                // disabled={currentPage === totalPages}
              >
                다음
              </button>
            </div>
            <form onSubmit={handleSearchSubmit} className="">
              <input
                type="text"
                value={searchQuery}
                onChange={handleSearchChange}
                className="text-center text-xs w-[30vh] ml-10 rounded-xl h-[5vh] border-1 border-solid border-[#000000] mr-4"
                placeholder="게시글 검색"
              />
            </form>
            <button
              className="w-[10vh] text-center rounded-xl bg-[#ffffff] shadow-md hover:bg-[#0095ff] hover:text-[#ffffff] "
              onClick={handleWriteButton}
            >
              글쓰기 ►
            </button>
          </div>
        </Wrapper>
      </div>

      <PlaylistIcon />
    </>
  );
};

export default Community;
