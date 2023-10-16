import React, { useState } from 'react';
import playlistimg from '../assets/images/Rectangle 64.png';

export type InsideCommu = {
  memberId: number;
  nickName: string;
  postId: number;
  title: string;
  text: string;
  viewCount: number;
};
const MyCommunity = ({ myCommunity }: { myCommunity: InsideCommu[] }) => {
  const [searchQuery, setSearchQuery] = useState('');
  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(event.target.value);
  };

  const handleSearchSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // 여기서 검색어(searchQuery)를 활용하여 검색 로직을 구현할 수 있습니다.
    console.log('검색어:', searchQuery);
  };
  return (
    <>
      <div className="w-full flex flex-col items-center mt-10">
        <div className="w-[875px] flex flex-row justify-between">
          <form onSubmit={handleSearchSubmit} className=" w-[700px] ">
            <input
              type="text"
              value={searchQuery}
              onChange={handleSearchChange}
              className="text-center text-xs w-[300px] ml-10 rounded-xl h-[30px] border-1 border-solid border-[#000000]"
              placeholder="게시글 검색"
            />
          </form>
          <div>
            <button className="w-[50px] text-center text-xs underline">조회순</button>
            {/* <button className="w-[50px] text-center text-xs underline">추천순</button> */}
          </div>
        </div>
        <div className="mt-5">
          <table className="flex flex-col w-[875px] items-center justify-center ">
            {myCommunity.map((el, idx) => (
              <tr className="flex w-[875px] h-[67px] items-center border-b-[1px] border-solid border-[#bdc2f8] justify-center">
                <td className="w-[50px] text-center text-xs">{idx + 1}</td>
                <td className="w-[100px]">
                  <img src={playlistimg} alt="임시사진" />
                </td>

                <td className="w-[700px] text-start text-xs">
                  <a href={`../community/${el.postId}`}>{el.title}</a>
                </td>
                <td className="w-[50px] text-center text-xs">{el.viewCount}</td>
              </tr>
            ))}
          </table>
        </div>
      </div>
    </>
  );
};

export default MyCommunity;
