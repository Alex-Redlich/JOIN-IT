import React from 'react'
import './pagination.css'

interface PaginationProps {
  itemsPerPage: number
  totalPages: number
  handlePageChange: (page: number) => void
  currentPage: number
}


function Pagination({itemsPerPage, totalPages, handlePageChange, currentPage}: PaginationProps) {

  const pageNumber = Math.ceil(totalPages / itemsPerPage);

  const handlePageChangeClick = (page: number) => {
    handlePageChange(page);
  }

  return (
    <ul className="row pagination">
      <div
        className="pageBoxBtn"
        onClick={currentPage === 1? () => {return ;} : () => handlePageChange(currentPage - 1)}
        style={{backgroundColor: currentPage === 1? '#5D6569' : '#dbf2f1'}}
        >
        <p className="page-link">
          {'<'}
        </p>
      </div>
      <div className="pageBoxPageNum">
        <p>{currentPage} / {pageNumber}</p>
      </div>
      <div
        className="pageBoxBtn"
        onClick={currentPage === pageNumber? () => {return ;} : () => handlePageChange(currentPage + 1)}
        style={{backgroundColor: currentPage === pageNumber? '#5D6569' : '#dbf2f1'}}
      >
        <p className="page-link">
          {'>'}
        </p>
      </div>
    </ul>
  )
}

export default Pagination