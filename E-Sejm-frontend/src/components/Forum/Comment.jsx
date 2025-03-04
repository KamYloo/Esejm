import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { BiSolidComment, BiSolidUpvote, BiSolidDownvote } from "react-icons/bi";
import { upvoteCommentAction, downvoteCommentAction } from "../../redux/PostService/Action.js";

function Comment({
                     commentId,
                     content,
                     score,
                     createDate,
                     author,
                     replies = [],
                     onReply
                 }) {
    const dispatch = useDispatch();

    const [currentScore, setCurrentScore] = useState(score);
    const [userVote, setUserVote] = useState(null); // null: brak głosu, "upvote": +1, "downvote": -1
    const [showReplyInput, setShowReplyInput] = useState(false);
    const [replyContent, setReplyContent] = useState("");

    // Inicjalizuj głos użytkownika z localStorage
    useEffect(() => {
        const savedVote = localStorage.getItem(`comment_vote_${commentId}`);
        if (savedVote) {
            setUserVote(savedVote);
        }
    }, [commentId]);

    const saveVoteToLocalStorage = (vote) => {
        localStorage.setItem(`comment_vote_${commentId}`, vote);
    };

    const countReplies = (comments) => {
        if (!comments || comments.length === 0) return 0;
        return comments.reduce((acc, comment) => {
            return acc + 1 + countReplies(comment.comment.replies);
        }, 0);
    };

    const totalRepliesCount = countReplies(replies);

    const handleUpvote = () => {
        if (!commentId || userVote === "upvote") return; // Nie wykonuj, jeśli już oddano upvote

        if (userVote === "downvote") {
            setCurrentScore(currentScore + 2);
        } else {
            setCurrentScore(currentScore + 1);
        }
        setUserVote("upvote");
        saveVoteToLocalStorage("upvote");

        dispatch(upvoteCommentAction(commentId));
    };

    const handleDownvote = () => {
        if (!commentId || userVote === "downvote") return; // Nie wykonuj, jeśli już oddano downvote

        if (userVote === "upvote") {
            setCurrentScore(currentScore - 2);
        } else {
            setCurrentScore(currentScore - 1);
        }
        setUserVote("downvote");
        saveVoteToLocalStorage("downvote");

        dispatch(downvoteCommentAction(commentId));
    };

    const handleReplySubmit = (e) => {
        e.preventDefault();
        if (!replyContent.trim()) {
            alert("Treść odpowiedzi nie może być pusta.");
            return;
        }
        onReply(commentId, replyContent);
        setReplyContent("");
        setShowReplyInput(false);
    };

    return (
        <div className="bg-blue-100 border border-blue-300 rounded-lg p-4 shadow-md max-w-4xl mx-auto mb-4">
            <div className="flex items-center justify-between mb-2">
                <div className="flex items-center space-x-2">
                    {author ? (
                        <>
                            <div className="w-8 h-8 rounded-full bg-gray-300 flex items-center justify-center">
                                <span className="text-gray-500 font-bold">👤</span>
                            </div>
                            <span className="text-sm font-semibold text-gray-800">@{author.nickName}</span>
                        </>
                    ) : (
                        <span className="text-sm text-gray-800 italic">Anonim</span>
                    )}
                </div>
                <div className="text-gray-500 italic">
                    {new Date(createDate).toLocaleString()}
                </div>
            </div>

            <div className="text-gray-900 text-sm mb-2">{content}</div>

            <div className="flex justify-between items-center text-xs">
                <div className="flex gap-4 mt-2 text-sm">
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
                        {totalRepliesCount}
                    </div>
                </div>
                <button
                    className="mt-2 bg-blue-500 text-white px-4 py-2 rounded-full hover:bg-blue-600"
                    onClick={() => setShowReplyInput(!showReplyInput)}
                >
                    Odpowiedz
                </button>
            </div>

            {showReplyInput && (
                <form onSubmit={handleReplySubmit} className="mt-4">
                    <input
                        type="text"
                        placeholder="Wpisz odpowiedź..."
                        value={replyContent}
                        onChange={(e) => setReplyContent(e.target.value)}
                        className="w-full px-4 py-2 border rounded"
                    />
                    <button
                        type="submit"
                        className="mt-2 bg-blue-500 text-white px-4 py-2 rounded-full hover:bg-blue-600"
                    >
                        Dodaj odpowiedź
                    </button>
                </form>
            )}

            {replies.length > 0 && (
                <div className="pl-6 mt-4">
                    {replies.map((reply) => (
                        <Comment
                            key={reply.comment.id}
                            commentId={reply.comment.id}
                            content={reply.comment.content}
                            author={reply.comment.author}
                            createDate={reply.comment.createDate || "Nieznana data"}
                            score={reply.comment.score || 0}
                            replies={reply.comment.replies || []}
                            onReply={onReply}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}

export default Comment;
