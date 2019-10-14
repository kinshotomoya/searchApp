package presentation.converter

import presentation.controllers.responses.SearchResponse
import useCase.search.{SearchCondition, SearchUseCaseResult}

object SearchResponseConverter {
  def convert(
    searchUseCaseResult: SearchUseCaseResult,
    searchCondition: SearchCondition
  ): SearchResponse = {
    SearchResponse(
      searchUseCaseResult.searchResult.metaData.took,
      searchUseCaseResult.searchResult.metaData.totalHits,
      searchUseCaseResult.searchResult.gyms.toList,
      searchCondition,
      searchUseCaseResult.searchResult.metaData
    )
  }
}
