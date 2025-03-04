import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { BiSolidComment, BiSolidUpvote, BiSolidDownvote } from "react-icons/bi";
import {
    upvotePostAction,
    downvotePostAction,
} from "../../redux/PostService/Action.js";

function Post({
                  postId,
                  title,
                  categories,
                  date,
                  message,
                  score,
                  author,
                  commentsCount,
              }) {
    
    const dispatch = useDispatch();
    const [currentScore, setCurrentScore] = useState(score);
    const [userVote, setUserVote] = useState(null); // null: no vote, "upvote": +1, "downvote": -1

    useEffect(() => {
        const savedVote = localStorage.getItem(`post_vote_${postId}`);
        if (savedVote) {
            setUserVote(savedVote);
        }
    }, [postId]);

    const saveVoteToLocalStorage = (vote) => {
        localStorage.setItem(`post_vote_${postId}`, vote);
    };

    const handleUpvote = () => {
        if (!postId) return;

        console.log("Sending upvote for postId:", postId);

        if (userVote === "upvote") {
            console.log("Already upvoted");
            return;
        }

        if (userVote === "downvote") {
            setCurrentScore(currentScore + 2);
        } else {
            setCurrentScore(currentScore + 1);
        }
        setUserVote("upvote");
        saveVoteToLocalStorage("upvote");

        dispatch(upvotePostAction(postId));
    };

    const handleDownvote = () => {
        if (!postId) return;

        console.log("Sending downvote for postId:", postId);

        if (userVote === "downvote") {
            console.log("Already downvoted");
            return;
        }

        if (userVote === "upvote") {
            setCurrentScore(currentScore - 2);
        } else {
            setCurrentScore(currentScore - 1);
        }
        setUserVote("downvote");
        saveVoteToLocalStorage("downvote");
        dispatch(downvotePostAction(postId));
    };

    return (
        <div className="bg-blue-200 border border-blue-300 rounded-xl p-4 shadow-md max-w-4xl mx-auto flex justify-between items-start">
            <div className="flex flex-col">
                <h2 className="text-lg font-bold text-gray-800">{title}</h2>
                <div className="flex items-center gap-2 text-sm text-gray-600">
                    {categories.map((category, index) => (
                        <span
                            key={index}
                            className="px-2 py-1 rounded-full text-gray-800 "
                        >
                            {category.name}
                        </span>
                    ))}
                    <span className="italic">{date}</span>
                </div>
                <p className="text-sm text-gray-800 mt-2">{message}</p>

                <div className="flex justify-between items-center text-xs mt-3">
                    <div className="flex gap-4 text-sm">
                        <button
                            onClick={handleUpvote}
                            className={`flex items-center text-lg font-bold ${
                                userVote === "upvote"
                                    ? "text-green-700"
                                    : userVote === "downvote"
                                        ? "text-gray-500"
                                        : "text-green-500"
                            } hover:text-green-800`}
                        >
                            <BiSolidUpvote />
                        </button>

                        <div className="flex items-center text-gray-700 font-bold text-lg">
                            {currentScore}
                        </div>

                        <button
                            onClick={handleDownvote}
                            className={`flex items-center text-lg font-bold ${
                                userVote === "downvote"
                                    ? "text-red-700"
                                    : userVote === "upvote"
                                        ? "text-gray-500"
                                        : "text-red-500"
                            } hover:text-red-800`}
                        >
                            <BiSolidDownvote />
                        </button>
                        <div className="flex items-center text-blue-600 font-bold gap-2 text-lg">
                            <BiSolidComment />
                            {commentsCount !== null ? commentsCount : "0"}
                        </div>
                    </div>
                </div>
            </div>
            <div className="flex flex-col items-center">
                <div className="w-10 h-10 bg-gray-300 rounded-full flex justify-center items-center">
                    <span className="text-gray-500 font-bold">👤</span>
                </div>
                <span className="text-sm font-semibold text-gray-800 mt-2">
                    {author.nickName}
                </span>
            </div>
        </div>
    );
}

export default Post;
