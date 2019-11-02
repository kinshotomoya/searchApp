package domain.search

import domain.gym.Gym
import useCase.search.SearchUseCaseResult
import zio.Task

case class SearchResult(
                       gyms: IndexedSeq[Gym],
                       metaData: MetaData
                       ) {
}
